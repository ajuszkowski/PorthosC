package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XControlFlowEvent;
import mousquetaires.languages.syntax.xgraph.events.fakes.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fakes.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fakes.XFakeEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.utils.exceptions.xgraph.XCompilatorUsageError;
import mousquetaires.utils.patterns.Builder;

import java.util.*;


public class XProcessBuilder extends Builder<XProcess> {

    private enum State {
        WaitingNextLinearEvent,

        WaitingAdditionalCommand,

        WaitingFirstTrueBranchEvent,
        WaitingFirstFalseBranchEvent,
        WaitingFirstLoopEvent,

        JustJumped,

        JustFinishedBranchingEvent,
        JustFinishedLoopEvent,
    }

    private final String processId;
    //private final ImmutableList.Builder<XLocalMemoryEvent> localMemoryEvents;
    //private final ImmutableList.Builder<XSharedMemoryEvent> sharedMemoryEvents;
    //private final ImmutableList.Builder<XComputationEvent> computationEvents;
    //private final ImmutableList.Builder<XBranchingEvent> branchingEvents;
    private final ImmutableList.Builder<XEvent> events;

    //private final ImmutableMap.Builder<XEvent, XEvent> trueBranchingJumpsMap;
    /*private*/ final HashMap<XEvent, XEvent> nextEventMap;
    /*private*/ final HashMap<XComputationEvent, XEvent> trueBranchingJumpsMap; //goto, if(true), while(true)
    /*private*/ final HashMap<XComputationEvent, XEvent> falseBranchingJumpsMap; //if(false)


    private State state = State.WaitingNextLinearEvent;

    // todo: add add/put methods with non-null checks
    private final Stack<NonlinearBlockInfo> loopsStack;
    private final Stack<NonlinearBlockInfo> branchingStack;
    private final Set<NonlinearBlockInfo> readyBranchings;
    private final Set<NonlinearBlockInfo> readyLoops;


    private XEvent previousEvent;

    //private XEvent lastTrueBranchEvent;
    //private XEvent lastFalseBranchEvent;

    private final XMemoryManager memoryManager;

    public XProcessBuilder(String processId, XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;

        this.processId = processId;
        //this.localMemoryEvents = new ImmutableList.Builder<>();
        //this.sharedMemoryEvents = new ImmutableList.Builder<>();
        //this.computationEvents = new ImmutableList.Builder<>();
        //this.branchingEvents = new ImmutableList.Builder<>();
        this.events = new ImmutableList.Builder<>();

        nextEventMap = new HashMap<>();
        trueBranchingJumpsMap = new HashMap<>();
        falseBranchingJumpsMap = new HashMap<>();

        branchingStack = new Stack<>();
        loopsStack = new Stack<>();
        readyBranchings = new HashSet<>();
        readyLoops = new HashSet<>();

        XEntryEvent entryEvent = new XEntryEvent(createEventInfo());
        add(entryEvent, events);
        previousEvent = entryEvent;
    }

    @Override
    public XProcess build() {
        processExitEvent();
        return new XProcess(this);
    }

    // --

    public String getProcessId() {
        return processId;
    }

    // --

    public XRegister copyToLocalMemory(XLocation shared) {
        XRegister tempLocal = memoryManager.newLocalMemoryUnit();
        emitMemoryEvent(tempLocal, shared);
        return tempLocal;
    }

    public XRegister copyToLocalMemoryIfNecessary(XMemoryUnit sharedOrLocal) {
        if (sharedOrLocal instanceof XLocation) {
            copyToLocalMemory((XLocation) sharedOrLocal);
        }
        else if (sharedOrLocal instanceof XRegister) {
            return (XRegister) sharedOrLocal;
        }
        throw new IllegalArgumentException(sharedOrLocal.getClass().getName());
    }

    // --

    // TODO: very bad, remove this!!!
    public XFakeEvent emitFakeEvent() {
        return new XFakeEvent(createEventInfo());
    }

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
        addAndProcessNextEvent(exitEvent);

        assert loopsStack.empty(): loopsStack.size();
        assert branchingStack.empty(): branchingStack.size();
        if (readyLoops.size() > 0) {
            state = State.JustFinishedLoopEvent;
            processNextEvent(exitEvent);
        }
        if (readyBranchings.size() > 0) {
            state = State.JustFinishedBranchingEvent;
            processNextEvent(exitEvent);
        }
    }

    private void addAndProcessNextEvent(XEvent nextEvent) {
        add(nextEvent, events);
        processNextEvent(nextEvent);
    }

    private void processNextEvent(XEvent nextEvent) {
        assert nextEvent != null;
        switch (state) {
            case WaitingNextLinearEvent: {
                if (previousEvent != null) {
                    setNextEvent(previousEvent, nextEvent);
                }
            }
            break;

            case WaitingFirstTrueBranchEvent: {
                branchingStack.peek().setFirstTrueBranchEvent(nextEvent);
            }
            break;

            case WaitingFirstFalseBranchEvent: {
                branchingStack.peek().setFirstFalseBranchEvent(nextEvent);
            }
            break;

            case WaitingFirstLoopEvent: {
                loopsStack.peek().setFirstTrueBranchEvent(nextEvent);
            }
            break;

            case JustJumped: {
                System.out.println("dead code: " + nextEvent);
            }
            break;

            case JustFinishedBranchingEvent: {
                for (NonlinearBlockInfo branching : readyBranchings) {
                    XEvent loopExitNode = nextEvent;
                    XComputationEvent conditionEvent = branching.conditionEvent;
                    if (branching.hasTrueBranch()) {
                        trueBranchingJumpsMap.put(conditionEvent, branching.firstTrueBranchEvent);
                        setNextEvent(branching.lastTrueBranchEvent, loopExitNode);
                    }
                    else {
                        throw new XCompilatorUsageError("Attempt to finish branching definition without true-branch");
                    }
                    if (branching.hasFalseBranch()) {
                        falseBranchingJumpsMap.put(conditionEvent, branching.firstFalseBranchEvent);
                        setNextEvent(branching.lastFalseBranchEvent, loopExitNode);
                    }
                    else {
                        falseBranchingJumpsMap.put(conditionEvent, loopExitNode);
                    }
                    assert !branching.hasBreakEvents();
                    assert !branching.hasContinueEvents();
                }
                readyBranchings.clear();
            }
            break;

            case JustFinishedLoopEvent: {
                for (NonlinearBlockInfo loop : readyLoops) {
                    XEvent loopExitNode = nextEvent;
                    XComputationEvent loopConditionEvent = loop.conditionEvent;

                    assert loop.lastFalseBranchEvent == null: loop.lastFalseBranchEvent.toString();
                    assert loop.firstFalseBranchEvent == null: loop.firstFalseBranchEvent.toString();

                    trueBranchingJumpsMap.put(loopConditionEvent, loop.firstTrueBranchEvent);
                    falseBranchingJumpsMap.put(loopConditionEvent, loopExitNode);
                    setNextEvent(loop.lastTrueBranchEvent, loopConditionEvent);
                    if (loop.hasContinueEvents()) {
                        for (XEvent continueingEvent : loop.continueingEvents) {
                            setNextEvent(continueingEvent, loopConditionEvent);
                        }
                    }
                    if (loop.hasBreakEvents()) {
                        for (XEvent breakingEvent : loop.breakingEvents) {
                            setNextEvent(breakingEvent, loopExitNode);
                        }
                    }
                }
                readyLoops.clear();
            }
            break;

            default:
                throw new XCompilatorUsageError("Received new event while in invalid state: " + state.name());
        }
        state = State.WaitingNextLinearEvent;
        previousEvent = nextEvent;
    }

    private void setNextEvent(XEvent from, XEvent to) {
        assert !(from instanceof XControlFlowEvent);
        nextEventMap.put(from, to);
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(getProcessId());
    }

    // to do: methodCall

    // -- BRANCHING ----------------------------------------------------------------------------------------------------

    public void startBranchingDefinition(XComputationEvent condition) {
        processStartBranching(condition, branchingStack);
    }

    public void startTrueBranch() {
        assert state == State.WaitingAdditionalCommand : state.name();
        state = State.WaitingFirstTrueBranchEvent;
    }

    public void finishTrueBranch() {
        NonlinearBlockInfo currentContext = branchingStack.peek();
        currentContext.setLastTrueBranchEvent(previousEvent);
        state = State.WaitingAdditionalCommand;
    }

    public void startFalseBranch() {
        assert state == State.WaitingAdditionalCommand : state.name();
        state = State.WaitingFirstFalseBranchEvent;
    }

    public void finishFalseBranch() {
        NonlinearBlockInfo currentContext = branchingStack.peek();
        currentContext.setLastFalseBranchEvent(previousEvent);
        state = State.WaitingAdditionalCommand;
    }

    public void finishBranchingDefinition() {
        readyBranchings.add(branchingStack.pop());
        state = State.JustFinishedBranchingEvent;
    }

    // -- LOOP ---------------------------------------------------------------------------------------------------------

    public void startLoopDefinition(XComputationEvent condition) {
        processStartBranching(condition, loopsStack);
    }

    public void startLoopBodyDefinition() {
        assert state == State.WaitingAdditionalCommand : state.name();
        state = State.WaitingFirstLoopEvent;
    }

    public void processLoopContinueStatement() {
        NonlinearBlockInfo currentContext = loopsStack.peek();
        currentContext.addContinueEvent(previousEvent);
        previousEvent = null;
        state = State.JustJumped;
    }

    public void processLoopBreakStatement() {
        NonlinearBlockInfo currentContext = loopsStack.peek();
        currentContext.addBreakEvent(previousEvent);
        previousEvent = null;
        state = State.JustJumped;
    }

    public void finishLoopBodyDefinition() {
        loopsStack.peek().setLastTrueBranchEvent(previousEvent);
        readyLoops.add(loopsStack.pop());
    }

    public void finishLoopDefinition() {
        state = State.JustFinishedLoopEvent;
    }

    // =================================================================================================================

    public ImmutableList<XEvent> buildEvents() {
        return events.build();
    }



    // =================================================================================================================

    private void processStartBranching(XComputationEvent conditionEvent, Stack<NonlinearBlockInfo> currentStack) {
        assert state == State.WaitingNextLinearEvent: state.name();
        currentStack.push(new NonlinearBlockInfo(conditionEvent));
        state = State.WaitingAdditionalCommand;
    }

    // =================================================================================================================

    private class NonlinearBlockInfo {
        private final XComputationEvent conditionEvent;
        private XEvent firstTrueBranchEvent;
        private XEvent firstFalseBranchEvent;

        private XEvent lastTrueBranchEvent;
        private XEvent lastFalseBranchEvent;

        private List<XEvent> continueingEvents;
        private List<XEvent> breakingEvents;

        //private XFakeEvent exitNode = new XFakeEvent(createEventInfo());

        public NonlinearBlockInfo(XComputationEvent conditionEvent) {
            this.conditionEvent = conditionEvent;
        }

        public void setFirstTrueBranchEvent(XEvent event) {
            assert event != conditionEvent;
            this.firstTrueBranchEvent = event;
        }

        public void setFirstFalseBranchEvent(XEvent event) {
            assert event != conditionEvent;
            this.firstFalseBranchEvent = event;
        }

        public void setLastTrueBranchEvent(XEvent event) {
            assert event != conditionEvent;
            this.lastTrueBranchEvent = event;
        }

        public void setLastFalseBranchEvent(XEvent event) {
            assert event != conditionEvent;
            this.lastFalseBranchEvent = event;
        }

        public void addContinueEvent(XEvent event) {
            assert event != conditionEvent;
            if (continueingEvents == null) {
                continueingEvents = new ArrayList<>();
            }
            continueingEvents.add(event);
        }

        public void addBreakEvent(XEvent event) {
            assert event != conditionEvent;
            if (breakingEvents == null) {
                breakingEvents = new ArrayList<>();
            }
            breakingEvents.add(event);
        }

        public boolean hasTrueBranch() {
            if (firstTrueBranchEvent != null) {
                assert lastTrueBranchEvent != null;
                return true;
            }
            return false;
        }

        public boolean hasFalseBranch() {
            if (firstFalseBranchEvent != null) {
                assert lastFalseBranchEvent != null;
                return true;
            }
            return false;
        }

        public boolean hasContinueEvents() {
            return continueingEvents != null && continueingEvents.size() > 0;
        }

        public boolean hasBreakEvents() {
            return breakingEvents != null && breakingEvents.size() > 0;
        }

        @Override
        public String toString() {
            return  conditionEvent + " { " + firstTrueBranchEvent + "... " + lastTrueBranchEvent +
                    " } else { " + firstFalseBranchEvent + "... " + lastFalseBranchEvent + " }";
        }
    }
}
