package mousquetaires.languages.syntax.xgraph.process.interpretation;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XFakeEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraphBuilder;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.exceptions.xgraph.XCompilerUsageError;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class XProcessInterpreter {

    public enum ContextState {
        Idle,
        WaitingAdditionalCommand,

        WaitingFirstConditionEvent,
        WaitingFirstSubBlockEvent,
        WaitingNextLinearEvent,

        //JustFinishedBranch,
        JustJumped,
        JustFinished,
    }

    public enum BranchKind {
        Then,
        Else,
    }

    private final String processId;
    private final XFlowGraphBuilder graphBuilder;
    private XFlowGraph result;

    // todo: add add/put methods with non-null checks
    private final Stack<XBlockContext> contextStack;
    private final Queue<XBlockContext> readyContexts;


    private XEvent previousEvent;


    private final XMemoryManager memoryManager;

    public XProcessInterpreter(String processId, XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        this.processId = processId; //todo: non-uniqueness case
        this.graphBuilder = new XFlowGraphBuilder(processId);
        contextStack = new Stack<>();
        readyContexts = new LinkedList<>();

        XBlockContext linearContext = new XBlockContext(XBlockContextKind.Linear);
        linearContext.state = XProcessInterpreter.ContextState.WaitingNextLinearEvent;
        contextStack.push(linearContext);

        addAndProcessEntryEvent();
    }


    public void finish() {
        addAndProcessExitEvent();
        //todo: verify
        assert contextStack.size() == 1; //linear entry context only
        assert readyContexts.isEmpty();
        result = graphBuilder.build();
    }

    public XFlowGraph getResult() {
        if (result == null) {
            finish();
        }
        return result;
    }

    // --

    public String getProcessId() {
        return processId;
    }

    // --

    public XLocalMemoryUnit copyToLocalMemoryIfNecessary(XMemoryUnit memoryUnit) {
        if (memoryUnit instanceof XLocation) {
            return copyToLocalMemory((XLocation) memoryUnit);
        }
        else if (memoryUnit instanceof XLocalMemoryUnit) { // also here: XComputationEvent
            return (XLocalMemoryUnit) memoryUnit;
        }
        throw new XInterpretationError("Illegal attempt to write to the local memory a memory unit of type "
                + memoryUnit.getClass().getSimpleName());
    }

    public XRegister copyToLocalMemory(XLocation shared) {
        XRegister tempLocal = memoryManager.newLocalMemoryUnit();
        emitMemoryEvent(tempLocal, shared);
        return tempLocal;
    }

    public XLocalMemoryUnit copyToLocalMemoryIfNecessary(XEvent event) {
        if (event instanceof XComputationEvent) {
            return (XComputationEvent) event;
        }
        throw new XInterpretationError("Attempt to write to the memory a non-memory event of type "
                + event.getClass().getSimpleName());
    }

    // --

    public XConstant getConstant(Object value) {
        return memoryManager.getConstant(value);
    }

    public XComputationEvent evaluateConstant(XConstant constant) {
        return emitComputationEvent(constant);
    }

    // --

    private void addAndProcessEntryEvent() {
        XEntryEvent entryEvent = new XEntryEvent(createEventInfo());
        graphBuilder.setSource(entryEvent);
        processNextEvent(entryEvent);
    }

    private void addAndProcessExitEvent() {
        XExitEvent exitEvent = new XExitEvent(createEventInfo());
        assert contextStack.size() == 1; //only entry linear context
        processNextEvent(exitEvent);
        graphBuilder.setSink(exitEvent);
    }

    ///**
    // * For modelling empty statement
    // */
    //public XFakeComputationEvent emitFakeComputationEvent() {
    //    XFakeComputationEvent event = new XFakeComputationEvent(createEventInfo());
    //    processNextEvent(event);
    //    return event;
    //}

    public XComputationEvent emitComputationEvent(XLocalMemoryUnit operand) {
        XComputationEvent event = new XNullaryComputationEvent(createEventInfo(), operand);
        processNextEvent(event);
        return event;
    }

    //public XComputationEvent emitComputationEvent(XRegister operand) {
    //    return emitUnaryComputationEvent(operand);
    //}
    //
    //public XComputationEvent emitComputationEvent(XConstant operand) {
    //    return emitUnaryComputationEvent(operand);
    //}

    public XComputationEvent emitComputationEvent(XZOperator operator, XLocalMemoryUnit operand) {
        XComputationEvent event = new XUnaryComputationEvent(createEventInfo(), operator, operand);
        processNextEvent(event);
        return event;
    }


    public XComputationEvent emitComputationEvent(XZOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        XComputationEvent event = new XBinaryComputationEvent(createEventInfo(), operator, firstOperand, secondOperand);
        processNextEvent(event);
        return event;
    }

    // --

    public XLocalMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        XRegisterMemoryEvent event = new XRegisterMemoryEvent(createEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(createEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(createEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    private XJumpEvent emitJumpEvent() {
        XJumpEvent event = new XJumpEvent(createEventInfo());
        processNextEvent(event);
        return event;
    }

    private void processNextEvent(XEvent nextEvent) {
        assert nextEvent != null;

        boolean alreadySetEdgeToNextEvent = false;

        if (!readyContexts.isEmpty()) {
            for (XBlockContext context : readyContexts) {

                boolean hasBreaks = context.hasBreakEvents();
                boolean hasContinues = context.hasContinueEvents();
                if (hasBreaks || hasContinues) {
                    assert context.firstElseBranchEvent == null : context.firstElseBranchEvent.toString();
                    assert context.lastElseBranchEvent == null  : context.lastElseBranchEvent.toString();

                    if (hasContinues) {
                        for (XFakeEvent continueingEvent : context.continueingEvents) {
                            graphBuilder.replaceEvent(continueingEvent, context.conditionEvent);
                        }
                    }
                    if (hasBreaks) {
                        for (XFakeEvent breakingEvent : context.breakingEvents) {
                            graphBuilder.replaceEvent(breakingEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                        }
                    }
                }

                if (context.firstThenBranchEvent != null) { // && !(context.firstThenBranchEvent instanceof XFakeEvent)) {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                        case Loop:
                            graphBuilder.addEdge(context.conditionEvent, context.firstThenBranchEvent);
                            break;
                    }
                }
                else {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addEdge(context.conditionEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            graphBuilder.addEdge(context.conditionEvent, context.conditionEvent);
                            break;
                    }
                }

                if (context.lastThenBranchEvent != null) {// && !(context.lastThenBranchEvent instanceof XFakeEvent)) {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addEdge(context.lastThenBranchEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            graphBuilder.addEdge(context.lastThenBranchEvent, context.entryEvent);
                            break;
                    }
                }

                if (context.firstElseBranchEvent != null) {// && !(context.firstElseBranchEvent instanceof XFakeEvent)) {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addAlternativeEdge(context.conditionEvent, context.firstElseBranchEvent);
                            break;
                        case Loop:
                            assert false;
                            break;
                    }
                }
                else {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                        case Loop:
                            graphBuilder.addAlternativeEdge(context.conditionEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                    }
                }

                if (context.lastElseBranchEvent != null) {// && !(context.lastElseBranchEvent instanceof XFakeEvent)) {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addEdge(context.lastElseBranchEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            assert false;
                            break;
                    }
                }
            }
            readyContexts.clear();
        }

        boolean isJumpEvent = nextEvent instanceof XJumpEvent;

        assert !contextStack.empty();
        for (int i = contextStack.size() - 1; i >= 0; i--) { //NonlinearBlock context : contextStack) {
            XBlockContext context = contextStack.get(i);

            //if (isJumpEvent && context.kind != XBlockContextKind.Loop) {
            //    continue;
            //}

            switch (context.state) {
                case WaitingNextLinearEvent: {
                    if (!alreadySetEdgeToNextEvent) {
                        if (previousEvent != null) {
                            graphBuilder.addEdge(previousEvent, nextEvent);
                        }
                        alreadySetEdgeToNextEvent = true;
                    }
                }
                break;

                case Idle: {
                    // do nothing
                }
                break;

                case WaitingAdditionalCommand: {
                    throw new IllegalStateException("waiting for an additional command before processing next event");
                }
                //break;

                case WaitingFirstConditionEvent: {
                    context.setEntryEvent(nextEvent);//already initialised above
                }
                break;

                case WaitingFirstSubBlockEvent: { //this case of stateStack should be on stack
                    assert nextEvent != context.conditionEvent;
                    switch (context.currentBranchKind) {
                        case Then:
                            // todo: do all nonlinear edge settings from ready hashset
                            context.firstThenBranchEvent = nextEvent;
                            break;
                        case Else:
                            context.firstElseBranchEvent = nextEvent;
                            break;
                    }
                    context.setState(ContextState.WaitingNextLinearEvent);
                    alreadySetEdgeToNextEvent = true; //delayed edge set (in traversing ready-contexts)
                }
                break;

                case JustJumped: {
                    // do nothing
                }
                break;

                case JustFinished: {
                    // do nothing
                }
                break;

                default:
                    throw new XCompilerUsageError("Received new event while in invalid stateStack: " + context.state.name());
            }
        }

        previousEvent = nextEvent;
    }


    private XEventInfo createEventInfo() {
        return new XEventInfo(processId);
    }

    // to do: methodCall

    // -- BRANCHING + LOOPS --------------------------------------------------------------------------------------------

    public void startBranchingBlockDefinition() {
        startNonlinearBlockDefinition(XBlockContextKind.Branching);
    }

    public void startLoopBlockDefinition() {
        startNonlinearBlockDefinition(XBlockContextKind.Loop);
    }

    public void startConditionDefinition() {
        XBlockContext context = contextStack.peek();
        context.setState(ContextState.WaitingFirstConditionEvent);
    }

    public void finishConditionDefinition() {
        if (!(previousEvent instanceof XComputationEvent)) {
            throw new XInterpretationError("Attempt to make branching on non-computation event "
                    + StringUtils.wrap(previousEvent.toString())
                    + " of type " + previousEvent.getClass().getSimpleName());
        }
        XBlockContext context = contextStack.peek();
        // TODO: send here Branching event!
        context.setConditionEvent((XComputationEvent) previousEvent);
        context.setState(ContextState.WaitingAdditionalCommand);
    }

    public void startThenBranchDefinition() {
        XBlockContext context = contextStack.peek();
        assert context.state == ContextState.WaitingAdditionalCommand : context.state.name();
        context.setState(ContextState.WaitingFirstSubBlockEvent);
        context.currentBranchKind = BranchKind.Then;
    }

    public void startElseBranchDefinition() {
        XBlockContext context = contextStack.peek();
        assert context.state == ContextState.WaitingAdditionalCommand : context.state.name();
        context.setState(ContextState.WaitingFirstSubBlockEvent);
        context.currentBranchKind = BranchKind.Else;
    }

    public void finishBranchDefinition() {
        XBlockContext context = contextStack.peek();
        assert context.currentBranchKind != null;
        if (previousEvent != context.conditionEvent) {
            switch (context.currentBranchKind) {
                case Then:
                    assert context.lastThenBranchEvent == null;
                    context.lastThenBranchEvent = previousEvent;
                    break;
                case Else:
                    assert context.lastElseBranchEvent == null;
                    context.lastElseBranchEvent = previousEvent;
                    break;
            }
        }
        context.currentBranchKind = null;
        context.setState(ContextState.WaitingAdditionalCommand);
        previousEvent = context.conditionEvent; //todo: check
    }


    public void finishNonlinearBlockDefinition() {
        XBlockContext context = contextStack.pop();
        context.currentBranchKind = null;
        context.setState(ContextState.JustFinished);
        readyContexts.add(context);
        previousEvent = null; //not to set too many linear jumps: e.g. `if (a) { while(b) do1(); } do2();`
    }

    public void processLoopBreakStatement() {
        XJumpEvent jumpEvent = emitJumpEvent();
        XBlockContext context = currentNearestLoopContext(); //context should peeked after the jump event was emitted
        context.addBreakEvent(jumpEvent);
        context.state = ContextState.JustJumped;
    }

    public void processLoopContinueStatement() {
        XJumpEvent jumpEvent = emitJumpEvent();
        XBlockContext context = currentNearestLoopContext(); //context should peeked after the jump event was emitted
        context.addContinueEvent(jumpEvent);
        context.state = ContextState.JustJumped;
    }

    private void startNonlinearBlockDefinition(XBlockContextKind kind) {
        contextStack.push(new XBlockContext(kind));
    }


    // =================================================================================================================

    private XBlockContext currentNearestLoopContext() {
        for (int i = contextStack.size() - 1; i >= 0; --i) {
            XBlockContext context = contextStack.get(i);
            if (context.kind == XBlockContextKind.Loop) {
                return context;
            }
        }
        throw new XCompilerUsageError("Not found any loop contexts");
    }
}
