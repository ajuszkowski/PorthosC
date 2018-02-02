package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XBranchingEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.exceptions.xgraph.XCompilatorUsageError;
import mousquetaires.utils.patterns.Builder;

import java.util.HashMap;
import java.util.Stack;


public class XProcessBuilder extends Builder<XProcess> {

    private enum State {
        WaitingNextLinearEvent,

        WaitingNextBranchingCommand,
        WaitingNextLoopCommand,

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

    //private final ImmutableMap.Builder<XEvent, XEvent> jumpsMap;
    /*private*/ final HashMap<XEvent, XEvent> nextMap;
    /*private*/ final HashMap<XEvent, XEvent> jumpsMap; //goto, if(true), while(true)
    /*private*/ final HashMap<XBranchingEvent, XEvent> falseBranchingJumpsMap; //if(false)


    private State state = State.WaitingNextLinearEvent;

    private final Stack<XBranchingEvent> branchingEventsStack;
    private final Stack<XComputationEvent> loopEntryNodesStack;

    private XEvent previousEvent;

    private XEvent lastTrueBranchEvent;
    private XEvent lastFalseBranchEvent;

    private final XMemoryManager memoryManager;

    public XProcessBuilder(String processId, XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;

        this.processId = processId;
        //this.localMemoryEvents = new ImmutableList.Builder<>();
        //this.sharedMemoryEvents = new ImmutableList.Builder<>();
        //this.computationEvents = new ImmutableList.Builder<>();
        //this.branchingEvents = new ImmutableList.Builder<>();
        this.events = new ImmutableList.Builder<>();

        nextMap = new HashMap<>();
        jumpsMap = new HashMap<>();
        falseBranchingJumpsMap = new HashMap<>();
        loopEntryNodesStack = new Stack<>();
        branchingEventsStack = new Stack<>();
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
                    nextMap.put(previousEvent, nextEvent);
                }
            break;

            case WaitingFirstTrueBranchEvent:
            case WaitingFirstFalseBranchEvent:
                if (previousEvent == null) {
                    throw new XCompilatorUsageError("Missing branching condition evaluation event");
                }
                if (!(previousEvent instanceof XBranchingEvent)) {
                    throw new XCompilatorUsageError("Invalid branching condition evaluation event: " +
                            StringUtils.wrap(previousEvent.toString()) + " of type " +
                            StringUtils.wrap(previousEvent.getClass().getSimpleName()));
                }
                XBranchingEvent branchingEvent = (XBranchingEvent) previousEvent;
                branchingEventsStack.push(branchingEvent);
                if (state == State.WaitingFirstTrueBranchEvent) {
                    jumpsMap.put(branchingEvent, nextEvent);
                }
                else {
                    falseBranchingJumpsMap.put(branchingEvent, nextEvent);
                }
            break;

            case JustFinishedBranchingEvent:
                if (lastTrueBranchEvent != null) {
                    nextMap.put(lastTrueBranchEvent, nextEvent);
                    lastTrueBranchEvent = null;
                } else {
                    throw new XCompilatorUsageError("Attempt to finish branching definition without true-branch");
                }
                if (lastFalseBranchEvent != null) {
                    nextMap.put(lastFalseBranchEvent, nextEvent);
                    lastFalseBranchEvent = null;
                }
            break;

            case JustFinishedLoopEvent:
                // ...
            break;

            default:
                throw new XCompilatorUsageError("Received new event while in invalid state: " + state.name());
        }
        state = State.WaitingNextLinearEvent;
        previousEvent = nextEvent;
    }


    private void resetCurrentEvent() {
        previousEvent = null;
    }

    private XEventInfo newEventInfo() {
        return new XEventInfo(getProcessId());
    }

    // to do: methodCall

    // -- BRANCHING ----------------------------------------------------------------------------------------------------

    public XBranchingEvent startBranching(XComputationEvent condition) {
        assert state == State.WaitingNextLinearEvent: state.name();
        XBranchingEvent branchingEvent = new XBranchingEvent(newEventInfo(), condition);
        processNextEvent(branchingEvent);
        state = State.WaitingNextBranchingCommand;
        return branchingEvent;
    }

    public void startTrueBranch() {
        if (state != State.WaitingNextBranchingCommand) {
            throw new XCompilatorUsageError();
        }
        state = State.WaitingFirstTrueBranchEvent;
    }

    public void finishTrueBranch() {
        assert state == State.WaitingNextLinearEvent: state.name();
        lastTrueBranchEvent = previousEvent;
        previousEvent = branchingEventsStack.peek();
        state = State.WaitingNextBranchingCommand;
    }

    public void startFalseBranch() {
        if (state != State.WaitingNextBranchingCommand) {
            throw new XCompilatorUsageError();
        }
        previousEvent =  branchingEventsStack.peek();
        state = State.WaitingFirstFalseBranchEvent;
    }

    public void finishFalseBranch() {
        assert state == State.WaitingNextLinearEvent: state.name();
        lastFalseBranchEvent = previousEvent;
        resetCurrentEvent();
        state = State.WaitingNextBranchingCommand;
    }

    public void finishBranching() {
        assert state == State.WaitingNextBranchingCommand: state.name();
        resetCurrentEvent();
        state = State.JustFinishedBranchingEvent;
    }

    // -- LOOP ---------------------------------------------------------------------------------------------------------




    // =================================================================================================================

    public ImmutableList<XEvent> buildEvents() {
        return events.build();
    }
}
