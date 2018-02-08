package mousquetaires.languages.syntax.xgraph.processes;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.fakes.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fakes.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.processes.contexts.NonlinearBlock;
import mousquetaires.languages.syntax.xgraph.processes.contexts.NonlinearBlockKind;
import mousquetaires.languages.syntax.xgraph.processes.contexts.NonlinearBlockStack;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xgraph.XCompilationError;
import mousquetaires.utils.exceptions.xgraph.XCompilerUsageError;
import mousquetaires.utils.patterns.Builder;

import java.util.HashSet;
import java.util.Set;


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
        True,
        False,
    }

    private final String processId;
    /*private*/ final XGraphBuilder graphBuilder;

    //private BranchingStatementType currentBranchingStatementType;
    //private BranchingStatementType previousBranchingStatementType;

    // todo: add add/put methods with non-null checks
    private final NonlinearBlockStack loopsStack;
    private final NonlinearBlockStack nonlinearStack;
    private final Set<NonlinearBlock> readyNonlinearBlocks;
    //private final Set<NonlinearBlockInfo> readyLoops;


    private XEvent previousEvent;

    //private XEvent lastTrueBranchEvent;
    //private XEvent lastFalseBranchEvent;

    private final XMemoryManager memoryManager;

    public XProcessBuilder(String processId, XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;

        this.processId = processId;

        this.graphBuilder = new XGraphBuilder();

        nonlinearStack = new NonlinearBlockStack();
        loopsStack = new NonlinearBlockStack();
        readyNonlinearBlocks = new HashSet<>();
        //readyLoops = new HashSet<>();

        XEntryEvent entryEvent = new XEntryEvent(createEventInfo());
        graphBuilder.addEvent(entryEvent);
        previousEvent = entryEvent;
    }

    @Override
    public XProcess build() {
        processExitEvent();

        //verify
        assert nonlinearStack.isEmpty();
        assert readyNonlinearBlocks.isEmpty();

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

    //// TODO: very bad, remove this!!!
    //public XFakeEvent emitFakeEvent() {
    //    return new XFakeEvent(createEventInfo());
    //}

    //public XComputationEvent evaluateLocalMemoryUnit(XLocalMemoryUnit localMemoryUnit) {
    //    return evaluateIfNecessary(localMemoryUnit);
    //}

    // --

    /**
     * For modelling empty statement
     */
    public XComputationEvent emitComputationEvent() {
        XRegister dummyTempMemory = memoryManager.newLocalMemoryUnit();
        return emitComputationEvent(dummyTempMemory);
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

    //private XJumpEvent emitJumpEvent() {
    //    XJumpEvent event = new XJumpEvent(createEventInfo());
    //    addAndProcessNextEvent(event);
    //    return event;
    //}

    private void processExitEvent() {
        XExitEvent exitEvent = new XExitEvent(createEventInfo());
        if (!nonlinearStack.empty()) {
            finishBranchingBlockDefinition();
            addAndProcessNextEvent(exitEvent);
        }
        else if (!loopsStack.empty()) {
            throw new NotImplementedException();
        }
        else {
            addAndProcessNextEvent(exitEvent);
        }
    }

    private void addAndProcessNextEvent(XEvent nextEvent) {
        graphBuilder.addEvent(nextEvent);
        processNextEvent(nextEvent);
    }


    private void processNextEvent(XEvent nextEvent) {
        assert nextEvent != null;

        boolean notTheTopOfTheStack = false;
        for (int i = nonlinearStack.size() - 1; i >= 0; i--) { //NonlinearBlock context : nonlinearStack) {
            NonlinearBlock context = nonlinearStack.get(i);

            switch (context.state) {
                case WaitingNextLinearEvent: {
                    if (previousEvent != null) { // && previousEvent != context.conditionEvent) {
                        graphBuilder.addEpsilonEdge(previousEvent, nextEvent);
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
                    nonlinearStack.peek().setEntryEvent(nextEvent);//already initialised above
                    //context.setState(ContextState.WaitingAdditionalCommand);
                }
                break;

                case WaitingFirstSubBlockEvent: { //this case of stateStack should be on stack
                    switch (context.currentBranchKind) {
                        case True:
                            graphBuilder.addTrueEdge(context.conditionEvent, nextEvent);
                            break;
                        case False:
                            graphBuilder.addFalseEdge(context.conditionEvent, nextEvent);
                            break;
                    }
                    context.setState(ContextState.WaitingNextLinearEvent);
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

            if (notTheTopOfTheStack) {
                //continue;
                context.setState(ContextState.Idle);
            }

            notTheTopOfTheStack = true;
        }

        if (!readyNonlinearBlocks.isEmpty()) {
            XEvent branchingExitNode = nextEvent;

            for (NonlinearBlock nonlinearBlock : readyNonlinearBlocks) {
                if (nonlinearBlock.hasTrueBranch()) {
                    //graphBuilder.addTrueEdge(nonlinearBlock.conditionEvent, nonlinearBlock.firstTrueBranchEvent);
                    graphBuilder.addEpsilonEdge(nonlinearBlock.lastTrueBranchEvent, branchingExitNode);
                }
                else {
                    //graphBuilder.addEpsilonEdge(nonlinearBlock.conditionEvent, previousConditionEvent);
                    graphBuilder.addEpsilonEdge(nonlinearBlock.conditionEvent, branchingExitNode);
                }
                if (nonlinearBlock.hasFalseBranch()) {
                    //graphBuilder.addFalseEdge(conditionEvent, branching.firstFalseBranchEvent);
                    graphBuilder.addEpsilonEdge(nonlinearBlock.lastFalseBranchEvent, branchingExitNode);
                }
                if (nonlinearBlock.kind == NonlinearBlockKind.Loop) {
                    NonlinearBlock loop = nonlinearBlock;
                    assert loop.lastFalseBranchEvent == null: loop.lastFalseBranchEvent.toString();
                    graphBuilder.addEpsilonEdge(loop.lastTrueBranchEvent, loop.conditionEvent);
                    if (loop.hasContinueEvents()) {
                        for (XEvent continueingEvent : loop.continueingEvents) {
                            graphBuilder.addEpsilonEdge(continueingEvent, loop.conditionEvent);
                        }
                    }
                    if (loop.hasBreakEvents()) {
                        for (XEvent breakingEvent : loop.breakingEvents) {
                            graphBuilder.addEpsilonEdge(breakingEvent, branchingExitNode);
                        }
                    }
                }
                else {
                    assert !nonlinearBlock.hasBreakEvents();
                    assert !nonlinearBlock.hasContinueEvents();
                }

                //previousConditionEvent = nonlinearBlock.conditionEvent;
            }
            readyNonlinearBlocks.clear();
        }

        previousEvent = nextEvent;
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(getProcessId());
    }

    // to do: methodCall

    // -----------------------------------------------------------------------------------------------------------------

    public void finishBranchDefinition() {
        NonlinearBlock context = nonlinearStack.peek();
        assert context.currentBranchKind != null;
        switch (context.currentBranchKind) {
            case True:
                context.lastTrueBranchEvent = previousEvent;
                break;
            case False:
                context.lastFalseBranchEvent = previousEvent;
        }
        context.currentBranchKind = null;
        context.setState(ContextState.WaitingAdditionalCommand);
    }

    // -- BRANCHING ----------------------------------------------------------------------------------------------------

    public void startBranchingBlockDefinition(NonlinearBlockKind type) {
        nonlinearStack.push(new NonlinearBlock(type));
        //stateStack.push(State.WaitingAdditionalCommand);
    }

    public void startConditionDefinition() {
        NonlinearBlock context = nonlinearStack.peek();
        context.setState(ContextState.WaitingFirstConditionEvent);
    }

    public void finishConditionDefinition() {
        if (!(previousEvent instanceof XComputationEvent)) {
            throw new XCompilationError("Attempt to make branching on non-computation event "
                    + StringUtils.wrap(previousEvent.toString())
                    + " of type " + previousEvent.getClass().getSimpleName());
        }
        NonlinearBlock context = nonlinearStack.peek();
        context.setConditionEvent((XComputationEvent) previousEvent);
        context.setState(ContextState.WaitingAdditionalCommand);
    }

    public void startTrueBranchDefinition() {
        NonlinearBlock context = nonlinearStack.peek();
        assert context.state == ContextState.WaitingAdditionalCommand : context.state.name();
        context.setState(ContextState.WaitingFirstSubBlockEvent);
        nonlinearStack.peek().currentBranchKind = BranchKind.True;
    }

    public void startFalseBranchDefinition() {
        NonlinearBlock context = nonlinearStack.peek();
        assert context.state == ContextState.WaitingAdditionalCommand : context.state.name();
        context.setState(ContextState.WaitingFirstSubBlockEvent);
        nonlinearStack.peek().currentBranchKind = BranchKind.False;
    }

    public void finishBranchingBlockDefinition() {
        NonlinearBlock context = nonlinearStack.pop();
        context.currentBranchKind = null;
        context.setState(ContextState.JustFinished);
        readyNonlinearBlocks.add(context);
    }

    // -- LOOP ---------------------------------------------------------------------------------------------------------

    //public void startLoopDefinition() {
    //    assert stateStack == State.WaitingNextLinearEvent: stateStack.name();
    //    loopsStack.push(new NonlinearBlockInfo());
    //    stateStack = State.WaitingAdditionalCommand;
    //}
    //
    //public void startLoopBodyDefinition() {
    //    assert stateStack == State.WaitingAdditionalCommand : stateStack.name();
    //    stateStack = State.WaitingFirstLoopEvent;
    //}
    //
    //public void processLoopContinueStatement() {
    //    NonlinearBlockInfo currentContext = loopsStack.peek();
    //    currentContext.addContinueEvent(previousEvent);
    //    previousEvent = null;
    //    stateStack = State.JustJumped;
    //}
    //
    //public void processLoopBreakStatement() {
    //    NonlinearBlockInfo currentContext = loopsStack.peek();
    //    currentContext.addBreakEvent(previousEvent);
    //    previousEvent = null;
    //    stateStack = State.JustJumped;
    //}
    //
    //public void finishLoopBodyDefinition() {
    //    loopsStack.peek().setLastTrueBranchEvent(previousEvent);
    //    readyLoops.add(loopsStack.pop());
    //}
    //
    //public void finishLoopDefinition() {
    //    stateStack = State.JustFinished;
    //}

    // =================================================================================================================

}
