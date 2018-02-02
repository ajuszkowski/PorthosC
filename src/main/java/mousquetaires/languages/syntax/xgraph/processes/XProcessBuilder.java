package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XBranchingEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.utils.exceptions.xgraph.XCompilatorUsageError;
import mousquetaires.utils.patterns.Builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


public class XProcessBuilder extends Builder<XProcess> {

    private enum State {
        WaitingNextLinearEvent,

        WaitingAdditionalCommand,

        WaitingFirstTrueBranchEvent,
        WaitingFirstFalseBranchEvent,
        WaitingFirstLoopEvent,

        //JustFinishedTrueBranching,
        //JustFinishedFalseBranching,

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
    /*private*/ final HashMap<XEvent, XEvent> trueBranchingJumpsMap; //goto, if(true), while(true)
    /*private*/ final HashMap<XBranchingEvent, XEvent> falseBranchingJumpsMap; //if(false)


    private State state = State.WaitingNextLinearEvent;

    // todo: add add/put methods with non-null checks
    private final Stack<BranchingBlockInfo> loopsStack;
    private final Stack<BranchingBlockInfo> branchingsStack;
    private final Set<BranchingBlockInfo> readyBranchings;
    private final Set<BranchingBlockInfo> readyLoops;


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

        branchingsStack = new Stack<>();
        loopsStack = new Stack<>();
        readyBranchings = new HashSet<>();
        readyLoops = new HashSet<>();
    }

    @Override
    public XProcess build() {
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


    /**
     * For modelling empty statement
     */
    public XComputationEvent emitComputationEvent() {
        XRegister dummyTempMemory = memoryManager.newLocalMemoryUnit();
        return emitComputationEvent(dummyTempMemory);
    }

    public XComputationEvent emitComputationEvent(XLocalMemoryUnit operand) {
        XComputationEvent event = new XNullaryComputationEvent(newEventInfo(), operand);
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

    public XComputationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit operand) {
        XComputationEvent event = new XUnaryOperationEvent(newEventInfo(), operator, operand);
        processNextEvent(event);
        return event;
    }


    public XComputationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        XComputationEvent event = new XBinaryOperationEvent(newEventInfo(), operator, firstOperand, secondOperand);
        processNextEvent(event);
        return event;
    }

    // --

    public XLocalMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        XRegisterMemoryEvent event = new XRegisterMemoryEvent(newEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(newEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(newEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }


    private void processNextEvent(XEvent nextEvent) {
        assert nextEvent != null;
        add(nextEvent, events);

        switch (state) {
            case WaitingNextLinearEvent:
                if (previousEvent != null) {
                    nextEventMap.put(previousEvent, nextEvent);
                }
            break;

            case WaitingFirstTrueBranchEvent:
                branchingsStack.peek().setFirstTrueBranchEvent(nextEvent);
            break;

            case WaitingFirstFalseBranchEvent:
                branchingsStack.peek().setFirstFalseBranchEvent(nextEvent);
            break;

            case WaitingFirstLoopEvent:
                loopsStack.peek().setFirstTrueBranchEvent(nextEvent);
            break;

            case JustFinishedBranchingEvent:
                for (BranchingBlockInfo readyBranching : readyBranchings) {
                    XBranchingEvent branchingConditionEvent = readyBranching.conditionEvent;

                    XEvent lastTrueBranchEvent = readyBranching.lastTrueBranchEvent;
                    if (lastTrueBranchEvent != null) {
                        trueBranchingJumpsMap.put(branchingConditionEvent, nextEvent);
                        nextEventMap.put(lastTrueBranchEvent, nextEvent);
                    } else {
                        throw new XCompilatorUsageError("Attempt to finish branching definition without true-branch");
                    }

                    XEvent lastFalseBranchEvent = readyBranching.lastFalseBranchEvent;
                    if (lastFalseBranchEvent != null) {
                        falseBranchingJumpsMap.put(branchingConditionEvent, nextEvent);
                        nextEventMap.put(lastFalseBranchEvent, nextEvent);
                    }
                }
                readyBranchings.clear();
            break;

            case JustFinishedLoopEvent:
                for (BranchingBlockInfo readyLoop : readyLoops) {
                    XBranchingEvent loopConditionEvent = readyLoop.conditionEvent;

                    assert readyLoop.lastFalseBranchEvent == null: readyLoop.lastFalseBranchEvent.toString();
                    assert readyLoop.firstFalseBranchEvent == null: readyLoop.firstFalseBranchEvent.toString();

                    trueBranchingJumpsMap.put(loopConditionEvent, readyLoop.firstTrueBranchEvent);
                    falseBranchingJumpsMap.put(loopConditionEvent, nextEvent);
                    nextEventMap.put(readyLoop.lastTrueBranchEvent, loopConditionEvent);
                }
                readyLoops.clear();
            break;

            default:
                throw new XCompilatorUsageError("Received new event while in invalid state: " + state.name());
        }
        state = State.WaitingNextLinearEvent;
        previousEvent = nextEvent;
    }

    private XEventInfo newEventInfo() {
        return new XEventInfo(getProcessId());
    }

    // to do: methodCall

    // -- BRANCHING ----------------------------------------------------------------------------------------------------

    public XBranchingEvent startBranching(XComputationEvent condition) {
        return processStartBranching(condition, branchingsStack);
    }

    public void startTrueBranch() {
        assert state == State.WaitingAdditionalCommand : state.name();
        state = State.WaitingFirstTrueBranchEvent;
    }

    public void finishTrueBranch() {
        BranchingBlockInfo currentContext = branchingsStack.peek();
        currentContext.setLastTrueBranchEvent(previousEvent);
        state = State.WaitingAdditionalCommand;
    }

    public void startFalseBranch() {
        assert state == State.WaitingAdditionalCommand : state.name();
        state = State.WaitingFirstFalseBranchEvent;
    }

    public void finishFalseBranch() {
        BranchingBlockInfo currentContext = branchingsStack.peek();
        currentContext.setLastFalseBranchEvent(previousEvent);
        state = State.WaitingAdditionalCommand;
    }

    public void finishBranching() {
        readyBranchings.add(branchingsStack.pop());
        state = State.JustFinishedBranchingEvent;
    }

    // -- LOOP ---------------------------------------------------------------------------------------------------------

    public XBranchingEvent startLoopDefinition(XComputationEvent condition) {
        return processStartBranching(condition, loopsStack);
    }

    public void startLoopBodyDefinition() {
        assert state == State.WaitingAdditionalCommand : state.name();
        state = State.WaitingFirstLoopEvent;
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

    private XBranchingEvent processStartBranching(XComputationEvent condition, Stack<BranchingBlockInfo> currentStack) {
        assert state == State.WaitingNextLinearEvent: state.name();
        XBranchingEvent branchingEvent = new XBranchingEvent(newEventInfo(), condition);
        processNextEvent(branchingEvent);
        currentStack.push(new BranchingBlockInfo(branchingEvent));
        state = State.WaitingAdditionalCommand;
        return branchingEvent;
    }

    // =================================================================================================================

    private class BranchingBlockInfo {
        private final XBranchingEvent conditionEvent;
        private XEvent firstTrueBranchEvent;
        private XEvent firstFalseBranchEvent;

        private XEvent lastTrueBranchEvent;
        private XEvent lastFalseBranchEvent;

        public BranchingBlockInfo(XBranchingEvent conditionEvent) {
            this.conditionEvent = conditionEvent;
        }

        public void setFirstTrueBranchEvent(XEvent firstTrueBranchEvent) {
            this.firstTrueBranchEvent = firstTrueBranchEvent;
        }

        public void setFirstFalseBranchEvent(XEvent firstFalseBranchEvent) {
            this.firstFalseBranchEvent = firstFalseBranchEvent;
        }

        public void setLastTrueBranchEvent(XEvent lastTrueBranchEvent) {
            this.lastTrueBranchEvent = lastTrueBranchEvent;
        }

        public void setLastFalseBranchEvent(XEvent lastFalseBranchEvent) {
            this.lastFalseBranchEvent = lastFalseBranchEvent;
        }

        @Override
        public String toString() {
            return  conditionEvent + " { " + firstTrueBranchEvent + "... " + lastTrueBranchEvent +
                    " } else { " + firstFalseBranchEvent + "... " + lastFalseBranchEvent + " }";
        }
    }
}
