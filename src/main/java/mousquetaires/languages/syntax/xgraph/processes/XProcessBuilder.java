package mousquetaires.languages.syntax.xgraph.processes;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fakes.XFakeComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fakes.XFakeEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.processes.contexts.Context;
import mousquetaires.languages.syntax.xgraph.processes.contexts.ContextKind;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.exceptions.xgraph.XCompilationError;
import mousquetaires.utils.exceptions.xgraph.XCompilerUsageError;
import mousquetaires.utils.patterns.Builder;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


public class XProcessBuilder extends Builder<XProcess> {

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
    /*private*/ final XGraphBuilder graphBuilder;

    // todo: add add/put methods with non-null checks
    //private final ContextStack loopsStack;
    private final Stack<Context> contextStack;
    private final Set<Context> readyContexts;
    //private final Set<NonlinearBlockInfo> readyLoops;


    private XEvent previousEvent;

    private final XMemoryManager memoryManager;

    public XProcessBuilder(String processId, XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;

        this.processId = processId;

        this.graphBuilder = new XGraphBuilder();

        contextStack = new Stack<>();
        //loopsStack = new ContextStack();
        readyContexts = new HashSet<>();
        //readyLoops = new HashSet<>();

        Context linearContext = new Context(ContextKind.Linear);
        linearContext.state = XProcessBuilder.ContextState.WaitingNextLinearEvent;
        contextStack.push(linearContext);

        XEntryEvent entryEvent = new XEntryEvent(createEventInfo());
        addAndProcessNextEvent(entryEvent);
    }

    @Override
    public XProcess build() {
        processExitEvent();

        //verify
        assert contextStack.size() == 1; //linear entry context only
        assert readyContexts.isEmpty();
        graphBuilder.TEMP_VERIFY();

        return new XProcess(this);
    }

    // --

    public String getProcessId() {
        return processId;
    }

    // --

    public XLocalMemoryUnit copyToLocalMemory(XMemoryUnit memoryUnit) {
        if (memoryUnit instanceof XLocation) {
            copyToLocalMemory((XLocation) memoryUnit);
        }
        else if (memoryUnit instanceof XLocalMemoryUnit) { // also here: XComputationEvent
            return (XLocalMemoryUnit) memoryUnit;
        }
        throw new XCompilationError("Illegal attempt to write to the local memory a memory unit of type "
                + memoryUnit.getClass().getSimpleName());
    }

    public XRegister copyToLocalMemory(XLocation shared) {
        XRegister tempLocal = memoryManager.newLocalMemoryUnit();
        emitMemoryEvent(tempLocal, shared);
        return tempLocal;
    }

    public XLocalMemoryUnit copyToLocalMemory(XEvent event) {
        if (event instanceof XComputationEvent) {
            return (XComputationEvent) event;
        }
        throw new XCompilationError("Attempt to write to the memory a non-memory event of type "
                + event.getClass().getSimpleName());
    }

    // --

    public XConstant getConstant(Object value) {
        return memoryManager.getConstant(value);
    }

    public XComputationEvent evaluateConstant(XConstant constant) {
        return emitComputationEvent(constant);
    }

    //
    //// TODO: very bad, remove this!!!
    //public XFakeEvent emitFakeEvent() {
    //    return new XFakeEvent(createEventInfo());
    //}
    //
    //public XComputationEvent evaluateLocalMemoryUnit(XLocalMemoryUnit localMemoryUnit) {
    //    return evaluateIfNecessary(localMemoryUnit);
    //}

    // --

    /**
     * For modelling empty statement
     */
    public XComputationEvent emitFakeComputationEvent() {
        XComputationEvent event = new XFakeComputationEvent(createEventInfo());
        addAndProcessNextEvent(event);
        return event;
    }

    public XComputationEvent emitComputationEvent(XLocalMemoryUnit operand) {
        XComputationEvent event = new XNullaryComputationEvent(createEventInfo(), operand);
        addAndProcessNextEvent(event);
        return event;
    }

    //public XComputationEvent emitComputationEvent(XRegister operand) {
    //    return emitUnaryComputationEvent(operand);
    //}
    //
    //public XComputationEvent emitComputationEvent(XConstant operand) {
    //    return emitUnaryComputationEvent(operand);
    //}

    public XComputationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit operand) {
        XComputationEvent event = new XUnaryOperationEvent(createEventInfo(), operator, operand);
        addAndProcessNextEvent(event);
        return event;
    }


    public XComputationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        XComputationEvent event = new XBinaryOperationEvent(createEventInfo(), operator, firstOperand, secondOperand);
        addAndProcessNextEvent(event);
        return event;
    }

    // --

    public XLocalMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        XRegisterMemoryEvent event = new XRegisterMemoryEvent(createEventInfo(), destination, source);
        addAndProcessNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(createEventInfo(), destination, source);
        addAndProcessNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(createEventInfo(), destination, source);
        addAndProcessNextEvent(event);
        return event;
    }

    private XJumpEvent emitJumpEvent() {
        XJumpEvent event = new XJumpEvent(createEventInfo());
        addAndProcessNextEvent(event);
        return event;
    }

    private void processExitEvent() {
        XExitEvent exitEvent = new XExitEvent(createEventInfo());
        assert contextStack.size() == 1; //only entry linear context
        //if (!contextStack.empty()) {
        //    finishNonlinearBlockDefinition();
        //    addAndProcessNextEvent(exitEvent);
        //}
        //else if (!loopsStack.empty()) {
        //    throw new NotImplementedException();
        //}
        //else {
        //
        //}
        addAndProcessNextEvent(exitEvent);
    }

    private void addAndProcessNextEvent(XEvent nextEvent) {
        if (!(nextEvent instanceof XFakeEvent)) { // TODO: looks like hack here
            graphBuilder.addEvent(nextEvent);
        }
        processNextEvent(nextEvent);
    }

    private void processNextEvent(XEvent nextEvent) {
        assert nextEvent != null;

        boolean alreadySetEdgeToNextEvent = false;

        if (!readyContexts.isEmpty()) {
            for (Context context : readyContexts) {

                if (context.firstThenBranchEvent instanceof XFakeComputationEvent) {
                    context.firstThenBranchEvent = null;
                }
                if (context.lastThenBranchEvent instanceof XFakeComputationEvent) {
                    context.lastThenBranchEvent = null;
                }
                if (context.firstElseBranchEvent instanceof XFakeComputationEvent) {
                    context.firstElseBranchEvent = null;
                }
                if (context.lastElseBranchEvent instanceof XFakeComputationEvent) {
                    context.lastElseBranchEvent = null;
                }

                if (context.firstThenBranchEvent != null) {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                        case Loop:
                            graphBuilder.addThenEdge(context.conditionEvent, context.firstThenBranchEvent);
                            break;
                    }
                }
                else {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addThenEdge(context.conditionEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            graphBuilder.addThenEdge(context.conditionEvent, context.conditionEvent);
                            break;
                    }
                }
                if (context.lastThenBranchEvent != null) {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addEpsilonEdge(context.lastThenBranchEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            graphBuilder.addEpsilonEdge(context.lastThenBranchEvent, context.entryEvent);
                            break;
                    }
                }
                /*else {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addElseEdge(context.conditionEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            // as it should be, no 'then' branch for loop
                            break;
                    }
                }*/

                //else {
                //    switch (context.kind) {
                //        case Linear:
                //            assert false;
                //            break;
                //        case Branching:
                //            //do nothing (should already done above)
                //            //graphBuilder.addThenEdge(context.conditionEvent, nextEvent);
                //            //alreadySetEdgeToNextEvent = true;
                //            break;
                //        case Loop:
                //            // todo: check in case: `while(1) { do1(); if(2) {break;} } do2();`
                //            //assert !(graphBuilder.elseBranchingJumpsMap.containsKey(context.conditionEvent));
                //            //graphBuilder.addElseEdge(context.conditionEvent, context.entryEvent);
                //            break;
                //    }
                //}

                if (context.firstElseBranchEvent != null) {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addElseEdge(context.conditionEvent, context.firstElseBranchEvent);
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
                            graphBuilder.addElseEdge(context.conditionEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true; //todo: check here
                        case Loop:
                            // ok, loops should have no else branches
                            break;
                    }
                }
                if (context.lastElseBranchEvent != null) {
                    switch (context.kind) {
                        case Linear:
                            assert false;
                            break;
                        case Branching:
                            graphBuilder.addEpsilonEdge(context.lastElseBranchEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            assert false;
                            break;
                    }
                }
                //else {
                //    switch (context.kind) {
                //        case Linear:
                //            assert false;
                //            break;
                //        case Branching:
                //            graphBuilder.addElseEdge(context.conditionEvent, nextEvent);
                //            alreadySetEdgeToNextEvent = true;
                //            break;
                //        case Loop:
                //            // do nothing
                //            break;
                //    }
                //
                //    //graphBuilder.addElseEdge(context.conditionEvent, nextEvent);
                //    //alreadySetEdgeToNextEvent = true;
                //}

                if (context.kind == ContextKind.Loop) {
                    graphBuilder.addElseEdge(context.conditionEvent, nextEvent);
                    assert context.firstElseBranchEvent == null : context.firstElseBranchEvent.toString();
                    assert context.lastElseBranchEvent == null  : context.lastElseBranchEvent.toString();

                    if (context.needToBindContinueEvents()) {
                        for (XEvent continueingEvent : context.continueingEvents) {
                            graphBuilder.addEpsilonEdge(continueingEvent, context.conditionEvent);
                        }
                    }
                    if (context.needToBindBreakEvents()) {
                        for (XEvent breakingEvent : context.breakingEvents) {
                            graphBuilder.addEpsilonEdge(breakingEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                        }
                    }
                } else {
                    assert !context.needToBindBreakEvents();
                    assert !context.needToBindContinueEvents();
                }
            }
            readyContexts.clear();
        }

        assert !contextStack.empty();
        for (int i = contextStack.size() - 1; i >= 0; i--) { //NonlinearBlock context : contextStack) {
            Context context = contextStack.get(i);
            switch (context.state) {
                case WaitingNextLinearEvent: {
                    if (!alreadySetEdgeToNextEvent) {
                        processNextLinearEvent(nextEvent);
                        alreadySetEdgeToNextEvent = true;
                    }
                    //else {
                    //    assert     graphBuilder.nextEventMap.containsValue(nextEvent)
                    //            || graphBuilder.thenBranchingJumpsMap.containsValue(nextEvent)
                    //            || graphBuilder.elseBranchingJumpsMap.containsValue(nextEvent)
                    //            : nextEvent;
                    //}
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
                            //graphBuilder.addElseEdge(context.conditionEvent, nextEvent);
                            //alreadySetEdgeToNextEvent = true;
                            break;
                    }
                    context.setState(ContextState.WaitingNextLinearEvent);
                    alreadySetEdgeToNextEvent = true; //delayed edge set
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


    private void processNextLinearEvent(XEvent nextEvent) {
        if (previousEvent != null) {
            graphBuilder.addEpsilonEdge(previousEvent, nextEvent);
        }
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(getProcessId());
    }

    // to do: methodCall

    // -- BRANCHING + LOOPS --------------------------------------------------------------------------------------------

    public void startNonlinearBlockDefinition(ContextKind kind) {
        contextStack.push(new Context(kind));
    }

    public void startConditionDefinition() {
        Context context = contextStack.peek();
        context.setState(ContextState.WaitingFirstConditionEvent);
    }

    public void finishConditionDefinition() {
        if (!(previousEvent instanceof XComputationEvent)) {
            throw new XCompilationError("Attempt to make branching on non-computation event "
                    + StringUtils.wrap(previousEvent.toString())
                    + " of type " + previousEvent.getClass().getSimpleName());
        }
        Context context = contextStack.peek();
        context.setConditionEvent((XComputationEvent) previousEvent);
        context.setState(ContextState.WaitingAdditionalCommand);
    }

    public void startThenBranchDefinition() {
        Context context = contextStack.peek();
        assert context.state == ContextState.WaitingAdditionalCommand : context.state.name();
        context.setState(ContextState.WaitingFirstSubBlockEvent);
        context.currentBranchKind = BranchKind.Then;
    }

    public void startElseBranchDefinition() {
        Context context = contextStack.peek();
        assert context.state == ContextState.WaitingAdditionalCommand : context.state.name();
        context.setState(ContextState.WaitingFirstSubBlockEvent);
        context.currentBranchKind = BranchKind.Else;
    }

    public void finishBranchDefinition() {
        Context context = contextStack.peek();
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
        previousEvent = null; //todo: check
    }


    public void finishNonlinearBlockDefinition() {
        Context context = contextStack.pop();
        context.currentBranchKind = null;
        context.setState(ContextState.JustFinished);
        readyContexts.add(context);
        previousEvent = null; //not to set too many epsilon jumps: e.g. `if (a) { while(b) do1(); } do2();`
    }

    public void processLoopBreakStatement() {
        Context context = currentNearestLoopContext();
        context.addBreakEvent(emitJumpEvent());
        context.state = ContextState.JustJumped;
    }

    public void processLoopContinueStatement() {
        Context context = currentNearestLoopContext();
        context.addContinueEvent(emitJumpEvent());
        context.state = ContextState.JustJumped;
    }

    // =================================================================================================================

    private Context currentNearestLoopContext() {
        for (int i = contextStack.size() - 1; i >= 0; --i) {
            Context context = contextStack.get(i);
            if (context.kind == ContextKind.Loop) {
                return context;
            }
        }
        throw new XCompilerUsageError("Not found any loop contexts");
    }
}
