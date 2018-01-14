package mousquetaires.languages.xrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.xrepr.events.memory.XMemoryEvent;
import mousquetaires.languages.xrepr.events.XBarrierEvent;
import mousquetaires.languages.xrepr.events.XCallEvent;
import mousquetaires.utils.patterns.Builder;


public class XProcessBuilder extends Builder<XProcess> {

    private final String name;
    private final ImmutableSet.Builder<XMemoryEvent> memoryEvents;
    private final ImmutableSet.Builder<XBarrierEvent> barrierEvents;
    private final ImmutableSet.Builder<XCallEvent> callEvents;


    // todo: relations

    public XProcessBuilder(String name) {
        this.name = name;
        memoryEvents = new ImmutableSet.Builder<>(); // todo: initial capacity, load factor ...
        callEvents = new ImmutableSet.Builder<>();
        barrierEvents = new ImmutableSet.Builder<>();
    }

    public XProcess build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        setBuilt();
        return new XProcess(this);
    }

    public String getName() {
        return name;
    }

    public void addMemoryEvent(XMemoryEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        memoryEvents.add(event);
    }

    public void addCallEvent(XCallEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        callEvents.add(event);
    }

    public void addBarrierEvent(XBarrierEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        barrierEvents.add(event);
    }

    public ImmutableSet<XMemoryEvent> getMemoryEvents() {
        if (!isBuilt()) {
            throw new RuntimeException(getNotYetFinishedMessage());
        }
        return memoryEvents.build();
    }

    public ImmutableSet<XCallEvent> getCallEvents() {
        if (!isBuilt()) {
            throw new RuntimeException(getNotYetFinishedMessage());
        }
        return callEvents.build();
    }

    public ImmutableSet<XBarrierEvent> getBarrierEvents() {
        if (!isBuilt()) {
            throw new RuntimeException(getNotYetFinishedMessage());
        }
        return barrierEvents.build();
    }
}
