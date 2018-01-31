package mousquetaires.languages.syntax.xrepr.processes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xrepr.events.XEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.*;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XConditionalJumpEvent;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XControlFlowEvent;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XUnconditionalJumpEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XSharedMemoryEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.utils.patterns.Builder;


public class XProcessBuilder extends Builder<XProcess> {

    private final String processId;

    private final ImmutableList.Builder<XLocalMemoryEvent> localMemoryEvents;
    private final ImmutableList.Builder<XSharedMemoryEvent> sharedMemoryEvents;
    private final ImmutableList.Builder<XComputationEvent> computationEvents;
    private final ImmutableList.Builder<XControlFlowEvent> jumpEvents;
    private final ImmutableMap.Builder<XEvent, XEvent> nextEvents;

    private XEvent currentEvent;

    public XProcessBuilder(String processId) {
        this.processId = processId;
        this.localMemoryEvents  = new ImmutableList.Builder<>();
        this.sharedMemoryEvents = new ImmutableList.Builder<>();
        this.computationEvents  = new ImmutableList.Builder<>();
        this.jumpEvents = new ImmutableList.Builder<>();
        this.nextEvents = new ImmutableMap.Builder<>();
    }

    @Override
    public XProcess build() {
        return new XProcess(this);
    }

    // --

    public XComputationEvent emitComputationEvent(XLocalMemoryUnit operand) {
        XComputationEvent event = new XNullaryComputationEvent(newEventInfo(), operand);
        add(event, computationEvents);
        setNextEvent(event);
        return event;
    }

    public XComputationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit operand) {
        XComputationEvent event = new XUnaryOperationEvent(newEventInfo(), operator, operand);
        add(event, computationEvents);
        setNextEvent(event);
        return event;
    }

    public XComputationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        XComputationEvent event = new XBinaryOperationEvent(newEventInfo(), operator, firstOperand, secondOperand);
        add(event, computationEvents);
        setNextEvent(event);
        return event;
    }

    // --

    public XLocalMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        XLocalMemoryEvent event = new XLocalMemoryEvent(newEventInfo(), destination, source);
        add(event, localMemoryEvents);
        setNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(newEventInfo(), destination, source);
        add(event, sharedMemoryEvents);
        setNextEvent(event);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(newEventInfo(), destination, source);
        add(event, sharedMemoryEvents);
        setNextEvent(event);
        return event;
    }

    // --

    public XUnconditionalJumpEvent emitUnconditionalJumpEvent(XEvent fromEvent, XEvent toEvent) {
        XUnconditionalJumpEvent event = new XUnconditionalJumpEvent(newEventInfo(), fromEvent, toEvent);
        add(event, jumpEvents);
        setNextEvent(event);
        return event;
    }

    public XConditionalJumpEvent emitConditionalJumpEvent(XComputationEvent condition, XEvent fromEvent, XEvent toEvent) {
        XConditionalJumpEvent event = new XConditionalJumpEvent(newEventInfo(), condition, fromEvent, toEvent);
        add(event, jumpEvents);
        setNextEvent(event);
        return event;
    }

    //methodCall

    // --



/*
    public XControlFlowEvent emitControlFlowEvent() {
        //add(event, jumpEvents);
        throw new NotImplementedException();
    }

    public XUnaryOperationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit memoryUnit) {
        XLocalMemoryUnit tempRegister = memoryManager.newLocalMemoryUnit();
        emitMemoryEvent(tempRegister, memoryUnit);  // `r := memoryUnit`
        return emitComputationEvent(operator, tempRegister);
    }

    public XUnaryOperationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit memoryUnit) {
        XUnaryOperationEvent event = new XUnaryOperationEvent(newEventInfo(), operator, memoryUnit);
        add(event, computationEvents);
        return event;
    }

    public XBinaryOperationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit left, XLocalMemoryUnit right) {
        XLocalMemoryUnit tempRegister = memoryManager.newLocalMemoryUnit();
        emitMemoryEvent(tempRegister, left); // `r := left`
        return emitComputationEvent(operator, tempRegister, right);
    }

    public XBinaryOperationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit left, XLocalMemoryUnit right) {
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

    public ImmutableList<XLocalMemoryEvent> buildLocalMemoryEvents() {
        return localMemoryEvents.build();
    }

    public ImmutableList<XSharedMemoryEvent> buildSharedMemoryEvents() {
        return sharedMemoryEvents.build();
    }

    public ImmutableList<XControlFlowEvent> buildControlFlowEvents() {
        return jumpEvents.build();
    }

    */

    public String getProcessId() {
        return processId;
    }

    private XEventInfo newEventInfo() {
        return new XEventInfo(getProcessId());
    }

    private void setNextEvent(XEvent nextEvent) {
        if (currentEvent != null) {
            nextEvents.put(currentEvent, nextEvent);
        }
        currentEvent = nextEvent;
    }

    public ImmutableList<XLocalMemoryEvent> buildLocalMemoryEvents() {
        return localMemoryEvents.build();
    }

    public ImmutableList<XSharedMemoryEvent> buildSharedMemoryEvents() {
        return sharedMemoryEvents.build();
    }

    public ImmutableList<XComputationEvent> buildComputationEvents() {
        return computationEvents.build();
    }

    public ImmutableList<XControlFlowEvent> buildJumpEvents() {
        return jumpEvents.build();
    }

    public ImmutableMap<XEvent, XEvent> buildNextEvents() {
        return nextEvents.build();
    }
}
