package mousquetaires.languages.syntax.xrepr.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.converters.toxrepr.XMemoryManager;
import mousquetaires.languages.syntax.xrepr.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XOperator;
import mousquetaires.languages.syntax.xrepr.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XControlFlowEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XSharedMemoryEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.patterns.Builder;


public abstract class XProcessBuilder extends Builder<XProcess> {

    private final XMemoryManager memoryManager;

    private final int processId;
    // data-flow events:
    private final ImmutableList.Builder<XLocalMemoryEvent> localMemoryEvents;
    private final ImmutableList.Builder<XSharedMemoryEvent> sharedMemoryEvents;
    // control-flow events:
    private final ImmutableList.Builder<XControlFlowEvent> controlFlowEvents;
    // computation events:
    private final ImmutableList.Builder<XComputationEvent> computationEvents;

    // todo: relations

    XProcessBuilder(int processId, XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        this.processId = processId;
        // todo: initial capacity, load factor ...
        this.localMemoryEvents = new ImmutableList.Builder<>();
        this.sharedMemoryEvents = new ImmutableList.Builder<>();
        this.computationEvents = new ImmutableList.Builder<>();
        this.controlFlowEvents = new ImmutableList.Builder<>();
    }

    public XLocalMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        XLocalMemoryEvent event = new XLocalMemoryEvent(newEventInfo(), destination, source);
        add(event, localMemoryEvents);
        return event;
    }

    public XSharedMemoryEvent emitMemoryEvent(XLocalMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(newEventInfo(), destination, source);
        add(event, sharedMemoryEvents);
        return event;
    }

    public XStoreMemoryEvent emitMemoryEvent(XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(newEventInfo(), destination, source);
        add(event, sharedMemoryEvents);
        return event;
    }

    public XControlFlowEvent emitControlFlowEvent() {
        //add(event, controlFlowEvents);
        throw new NotImplementedException();
    }

    public XUnaryOperationEvent emitComputationEvent(XOperator operator, XSharedMemoryUnit memoryUnit) {
        XLocalMemoryUnit tempRegister = memoryManager.newTempLocalMemoryUnit();
        emitMemoryEvent(tempRegister, memoryUnit);  // `r := memoryUnit`
        return emitComputationEvent(operator, tempRegister);
    }

    public XUnaryOperationEvent emitComputationEvent(XOperator operator, XLocalMemoryUnit memoryUnit) {
        XUnaryOperationEvent event = new XUnaryOperationEvent(newEventInfo(), operator, memoryUnit);
        add(event, computationEvents);
        return event;
    }

    public XBinaryOperationEvent emitComputationEvent(XOperator operator, XSharedMemoryUnit left, XLocalMemoryUnit right) {
        XLocalMemoryUnit tempRegister = memoryManager.newTempLocalMemoryUnit();
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

    public int getProcessId() {
        return processId;
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
        return controlFlowEvents.build();
    }

    private XEventInfo newEventInfo() {
        return new XEventInfo(processId);
    }
}
