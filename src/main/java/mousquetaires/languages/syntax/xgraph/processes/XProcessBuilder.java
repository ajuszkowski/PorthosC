package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XConditionalJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XControlFlowEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.utils.patterns.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class XProcessBuilder extends Builder<XProcess> {

    private final String processId;
    private final ImmutableList.Builder<XRegisterMemoryEvent> localMemoryEvents;
    private final ImmutableList.Builder<XSharedMemoryEvent> sharedMemoryEvents;
    private final ImmutableList.Builder<XComputationEvent> computationEvents;
    private final ImmutableList.Builder<XControlFlowEvent> controlFlowEvents;
    //private final ImmutableMap.Builder<XEvent, XEvent> nextEvents;

    private final Stack<XConditionalJumpEvent> conditionalContextStack;
    private final List<XEvent> lastBranchEvents;
    private final List<XEvent> currentEventList;


    public XProcessBuilder(String processId) {
        this.processId = processId;
        this.localMemoryEvents = new ImmutableList.Builder<>();
        this.sharedMemoryEvents = new ImmutableList.Builder<>();
        this.computationEvents = new ImmutableList.Builder<>();
        this.controlFlowEvents = new ImmutableList.Builder<>();
        //this.nextEvents = new ImmutableMap.Builder<>();
        conditionalContextStack = new Stack<>();
        lastBranchEvents = new ArrayList<>(2);
        currentEventList = new ArrayList<>(20); //init by average size of a linear block
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

    public XComputationEvent emitComputationEvent(XLocalMemoryUnit operand) {
        XComputationEvent event = new XNullaryComputationEvent(newEventInfo(), operand);
        processEvent(event, computationEvents);
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
        processEvent(event, computationEvents);
        return event;
    }


    public XComputationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        XComputationEvent event = new XBinaryOperationEvent(newEventInfo(), operator, firstOperand, secondOperand);
        processEvent(event, computationEvents);
        return event;
    }

    // --

    public XLocalMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        XRegisterMemoryEvent event = new XRegisterMemoryEvent(newEventInfo(), destination, source);
        processEvent(event, localMemoryEvents);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(newEventInfo(), destination, source);
        processEvent(event, sharedMemoryEvents);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(newEventInfo(), destination, source);
        processEvent(event, sharedMemoryEvents);
        return event;
    }


    //methodCall

    // --


    // -- helpers:


/*
    public XControlFlowEvent emitControlFlowEvent() {
        //add(event, controlFlowEvents);
        throw new NotImplementedException();
    }

    public XUnaryOperationEvent emitComputationEvent(XOperator operator, XRegister memoryUnit) {
        XRegister tempRegister = memoryManager.newLocalMemoryUnit();
        emitMemoryEvent(tempRegister, memoryUnit);  // `r := memoryUnit`
        return emitComputationEvent(operator, tempRegister);
    }

    public XUnaryOperationEvent emitComputationEvent(XOperator operator, XRegister memoryUnit) {
        XUnaryOperationEvent event = new XUnaryOperationEvent(newEventInfo(), operator, memoryUnit);
        add(event, computationEvents);
        return event;
    }

    public XBinaryOperationEvent emitComputationEvent(XOperator operator, XRegister left, XRegister right) {
        XRegister tempRegister = memoryManager.newLocalMemoryUnit();
        emitMemoryEvent(tempRegister, left); // `r := left`
        return emitComputationEvent(operator, tempRegister, right);
    }

    public XBinaryOperationEvent emitComputationEvent(XOperator operator, XRegister left, XRegister right) {
        XBinaryOperationEvent event = new XBinaryOperationEvent(newEventInfo(), operator, left, right);
        add(event, computationEvents);
        return event;
    }

    public XBarrierEvent emitBarrierEvent() {
        //add(event, barrierEvents);
        throw new NotImplementedException();
    }


    public ImmutableList<XComputationEvent> buildComputationEvents() {
        return computationEvents.build();
    }

    public ImmutableList<XRegisterMemoryEvent> buildLocalMemoryEvents() {
        return localMemoryEvents.build();
    }

    public ImmutableList<XSharedMemoryEvent> buildSharedMemoryEvents() {
        return sharedMemoryEvents.build();
    }

    public ImmutableList<XControlFlowEvent> buildControlFlowEvents() {
        return controlFlowEvents.build();
    }

    */

    //public XUnconditionalJumpEvent emitUnconditionalJumpEvent(XEvent fromEvent, XEvent toEvent) {
    //    XUnconditionalJumpEvent event = new XUnconditionalJumpEvent(newEventInfo(), fromEvent, toEvent);
    //    processEvent(event, );
    //    return event;
    //}
    //
    //public XConditionalJumpEvent emitInvertedConditionalJumpEvent(XComputationEvent loopCondition, XEvent toEvent) {
    //    return emitConditionalJumpEvent(not(loopCondition), toEvent);
    //}
    //
    //public XConditionalJumpEvent emitConditionalJumpEvent(XComputationEvent jumpCondition, XEvent toEvent) {
    //    XConditionalJumpEvent event = new XConditionalJumpEvent(newEventInfo(), jumpCondition, toEvent);
    //    conditionalContextStack.push(jumpCondition);
    //    return event;
    //}

    // TODO: break loops with contexts and exit-nodes
    //public XConditionalJumpEvent emitBreakLoopEvent(XComputationEvent loopCondition) {
    //    return emitConditionalJumpEvent(not(loopCondition), )
    //}

    public void startBranchingDefinition(XComputationEvent conditionEvent) {
        XConditionalJumpEvent jumpEvent = new XConditionalJumpEvent(newEventInfo(), conditionEvent);
        conditionalContextStack.push(jumpEvent);
        add(jumpEvent, controlFlowEvents);
        currentEventList.clear();
    }

    public void finishTrueBranchDefinition(XEvent firstEvent) {
        XConditionalJumpEvent conditionalJump = conditionalContextStack.peek();
        conditionalJump.setNextEvent(firstEvent);

        assert currentEventList.size() > 0;
        lastBranchEvents.addAll(currentEventList);
        currentEventList.clear();
    }


    public void finishFalseBranchDefinition(XEvent firstEvent) {
        XConditionalJumpEvent conditionalJump = conditionalContextStack.peek();
        conditionalJump.setAlternativeNext(firstEvent);

        assert currentEventList.size() > 0;
        lastBranchEvents.addAll(currentEventList);
        currentEventList.clear();
    }

    public XConditionalJumpEvent finishBranchingEventDefinition() {
        assert lastBranchEvents.size() > 0;
        currentEventList.addAll(lastBranchEvents);
        return conditionalContextStack.pop();
    }

    public ImmutableList<XRegisterMemoryEvent> buildLocalMemoryEvents() {
        return localMemoryEvents.build();
    }

    public ImmutableList<XSharedMemoryEvent> buildSharedMemoryEvents() {
        return sharedMemoryEvents.build();
    }

    public ImmutableList<XComputationEvent> buildComputationEvents() {
        return computationEvents.build();
    }

    public ImmutableList<XControlFlowEvent> buildControlFlowEvents() {
        return controlFlowEvents.build();
    }




    private XEventInfo newEventInfo() {
        return new XEventInfo(getProcessId());
    }

    private <T extends XEvent> void processEvent(T event, ImmutableList.Builder<T> collection) {
        super.add(event, collection);
        processNextEvent(event);
    }

    private <S extends XEvent> void processNextEvent(S nextEvent) {
        for (XEvent currentEvent : currentEventList) {
            if (currentEvent != null && !(currentEvent instanceof XControlFlowEvent)) {
                currentEvent.setNextEvent(nextEvent);
            }
        }
        setNextLinearEvent(nextEvent);
    }

    private <S extends XEvent> void setNextLinearEvent(S nextEvent) {
        currentEventList.clear();
        currentEventList.add(nextEvent);
    }
}
