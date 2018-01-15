package mousquetaires.languages.converters.toxrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XControlFlowEvent;
import mousquetaires.languages.syntax.xrepr.XProcess;
import mousquetaires.languages.syntax.xrepr.events.*;
import mousquetaires.languages.syntax.xrepr.events.memory.*;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;
import mousquetaires.utils.patterns.Builder;


public class XProcessBuilder extends Builder<XProcess> {

    private final String processName;
    private final ImmutableSet.Builder<XLocalMemoryEvent> initialWriteEvents;
    private final ImmutableSet.Builder<XLocalMemoryEvent> localMemoryEvents;
    private final ImmutableSet.Builder<XSharedMemoryEvent> sharedMemoryEvents;
    private final ImmutableSet.Builder<XBarrierEvent> barrierEvents;
    private final ImmutableSet.Builder<XControlFlowEvent> controlFlowEvents;


    // todo: relations

    XProcessBuilder(String processName) {
        this.processName = processName;
        // todo: initial capacity, load factor ...
        this.initialWriteEvents = new ImmutableSet.Builder<>();
        this.localMemoryEvents = new ImmutableSet.Builder<>();
        this.sharedMemoryEvents = new ImmutableSet.Builder<>();
        this.controlFlowEvents = new ImmutableSet.Builder<>();
        this.barrierEvents = new ImmutableSet.Builder<>();
    }

    @Override
    public XProcess build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        finish();
        return new XProcess(this);
    }

    public XInitialWriteEvent addInitialWriteEvent(XLocalMemoryUnit destination, XValue source) {
        XInitialWriteEvent event = new XInitialWriteEvent(getNewEventInfo(), destination, source);
        add(event, initialWriteEvents);
        return event;
    }

    public XLocalMemoryEvent addLocalMemoryEvent(XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        XLocalMemoryEvent event = new XLocalMemoryEvent(getNewEventInfo(), destination, source);
        add(event, localMemoryEvents);
        return event;
    }

    public XSharedMemoryEvent addSharedMemoryEvent(XLocalMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(getNewEventInfo(), destination, source);
        add(event, sharedMemoryEvents);
        return event;
    }

    public XStoreMemoryEvent addSharedMemoryEvent(XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(getNewEventInfo(), destination, source);
        add(event, sharedMemoryEvents);
        return event;
    }

    public void addControlFlowEvent() {
        //add(event, controlFlowEvents);
    }

    public void addBarrierEvent() {
        //add(event, barrierEvents);
    }

    public String getProcessName() {
        return processName;
    }

    public ImmutableSet<XLocalMemoryEvent> buildInitialWriteEvents() {
        return initialWriteEvents.build();
    }

    public ImmutableSet<XLocalMemoryEvent> buildLocalMemoryEvents() {
        return localMemoryEvents.build();
    }

    public ImmutableSet<XSharedMemoryEvent> buildSharedMemoryEvents() {
        return sharedMemoryEvents.build();
    }

    public ImmutableSet<XBarrierEvent> buildBarrierEvents() {
        return barrierEvents.build();
    }

    public ImmutableSet<XControlFlowEvent> buildControlFlowEvents() {
        return controlFlowEvents.build();
    }

    private XEventInfo getNewEventInfo() {
        return new XEventInfo(processName);
    }
}
