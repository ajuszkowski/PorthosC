package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessBuilder;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;
import mousquetaires.utils.exceptions.xgraph.XInterpreterUsageError;

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

    private final XProcessId processId;
    private final XProcessBuilder graphBuilder;
    private XProcess result;

    // todo: add add/put methods with non-null checks
    private final Stack<XBlockContext> contextStack;
    private final Queue<XBlockContext> readyContexts;

    private final XMemoryManager memoryManager;

    private XEvent previousEvent;


    public XProcessInterpreter(XProcessId processId, XMemoryManager memoryManager) {
        this.processId = processId; //todo: non-uniqueness case
        this.memoryManager = memoryManager;
        this.graphBuilder = new XProcessBuilder(processId);
        contextStack = new Stack<>();
        readyContexts = new LinkedList<>();

        XBlockContext linearContext = new XBlockContext(XBlockContextKind.Sequential);
        linearContext.state = XProcessInterpreter.ContextState.WaitingNextLinearEvent;
        contextStack.push(linearContext);
    }


    public void finish() {
        emitExitEvent();
        //todo: verify
        assert contextStack.size() == 1; //linear entry context only
        assert readyContexts.isEmpty();
        result = graphBuilder.build();
    }

    public XProcess getResult() {
        if (result == null) {
            finish();
        }
        return result;
    }

    // --

    public XProcessId getProcessId() {
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

    public XRegister copyToLocalMemory(XSharedMemoryUnit shared) {
        XRegister tempLocal = memoryManager.getTempRegister(shared.getType());
        emitMemoryEvent(tempLocal, shared);
        return tempLocal;
    }

    // --

    public XConstant getConstant(Object value, Type type) {
        return XConstant.create(value, type);
    }

    public XComputationEvent evaluateMemoryUnit(XMemoryUnit memoryUnit) { //todo: type?
        XLocalMemoryUnit localMemoryUnit = copyToLocalMemoryIfNecessary(memoryUnit);
        return emitComputationEvent(XUnaryOperator.NoOperation, localMemoryUnit);
    }

    public XComputationEvent evaluateConstant(XConstant constant) {
        return emitComputationEvent(XUnaryOperator.NoOperation, constant);
    }

    // --

    public XEntryEvent emitEntryEvent() {
        XEntryEvent entryEvent = new XEntryEvent(createEventInfo());
        graphBuilder.setSource(entryEvent);
        processNextEvent(entryEvent);
        return entryEvent;
    }

    public XExitEvent emitExitEvent() {
        XExitEvent exitEvent = new XExitEvent(createEventInfo());
        assert contextStack.size() == 1; //only entry linear context
        processNextEvent(exitEvent);
        graphBuilder.setSink(exitEvent);
        return exitEvent;
    }

    ///**
    // * For modelling empty statement
    // */
    public XNopEvent emitNopEvent() {
        XNopEvent event = new XNopEvent(createEventInfo());
        processNextEvent(event);
        return event;
    }

    public XComputationEvent emitComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand) {
        XComputationEvent event = new XUnaryComputationEvent(createEventInfo(), operator, operand);
        processNextEvent(event);
        return event;
    }


    public XComputationEvent emitComputationEvent(XBinaryOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        XComputationEvent event = new XBinaryComputationEvent(createEventInfo(), operator, firstOperand, secondOperand);
        processNextEvent(event);
        return event;
    }

    // --

    public XLocalMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        XRegisterMemoryEvent event = new XRegisterMemoryEvent(createEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(createEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XSharedLvalueMemoryUnit destination, XLocalMemoryUnit source) {
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

                if (context.firstThenBranchEvent != null) {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no then-events are allowed for linear statements";
                            break;
                        case Branching:
                        case Loop:
                            graphBuilder.addEdge(true, context.conditionEvent, context.firstThenBranchEvent);
                            break;
                    }
                }
                else {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no then-events are allowed for linear statements";
                            break;
                        case Branching:
                        case Loop:
                            assert false : "every branching statement must have at least one then-event (NOP if none)";
                            break;
                    }
                }
                if (context.lastThenBranchEvent != null) {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no then-events are allowed for linear statements";
                            break;
                        case Branching:
                            graphBuilder.addEdge(true, context.lastThenBranchEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            graphBuilder.addEdge(true, context.lastThenBranchEvent, context.entryEvent);
                            break;
                    }
                }

                if (context.firstElseBranchEvent != null) {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no else-events are allowed for linear statements";
                            break;
                        case Branching:
                            graphBuilder.addEdge(false, context.conditionEvent, context.firstElseBranchEvent);
                            break;
                        case Loop:
                            assert false : "no else-events are allowed for loop staements";
                            break;
                    }
                }
                else {
                    switch (context.kind) {
                        case Sequential:
                            assert false;
                            break;
                        case Branching:
                            assert false : "every branching statement must have at least one then-event (NOP if none)";
                            break;
                        case Loop:
                            break;
                    }
                }
                if (context.lastElseBranchEvent != null) {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no else-events are allowed for linear statements";
                            break;
                        case Branching:
                            graphBuilder.addEdge(true, context.lastElseBranchEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            assert false : "no else-events are allowed for loop staements";
                            break;
                    }
                }
            }
            readyContexts.clear();
        }


        assert !contextStack.empty();
        for (int i = contextStack.size() - 1; i >= 0; i--) { //NonlinearBlock context : contextStack) {
            XBlockContext context = contextStack.get(i);

            switch (context.state) {
                case WaitingNextLinearEvent: {
                    if (!alreadySetEdgeToNextEvent) {
                        if (previousEvent != null) {
                            graphBuilder.addEdge(true,previousEvent, nextEvent);
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
                    throw new XInterpreterUsageError("Received new event while in invalid stateStack: " + context.state.name());
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
                    if (context.firstThenBranchEvent == null) { //TODO: kostyl?
                        context.firstThenBranchEvent = previousEvent;
                    }
                    break;
                case Else:
                    assert context.lastElseBranchEvent == null;
                    context.lastElseBranchEvent = previousEvent;
                    if (context.firstElseBranchEvent == null) { //TODO: kostyl?
                        context.firstElseBranchEvent = previousEvent;
                    }
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
        throw new XInterpreterUsageError("Not found any loop contexts");
    }
}
