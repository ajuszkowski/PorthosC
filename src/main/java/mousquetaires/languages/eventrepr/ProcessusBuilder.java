package mousquetaires.languages.eventrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.eventrepr.events.BarrierEvent;
import mousquetaires.languages.eventrepr.events.CallEvent;
import mousquetaires.languages.eventrepr.events.MemoryEvent;
import mousquetaires.patterns.Builder;


public class ProcessusBuilder extends Builder<Processus> {

    private boolean built;
    private final String name;
    private final ImmutableSet.Builder<MemoryEvent> memoryEvents;
    private final ImmutableSet.Builder<BarrierEvent> barrierEvents;
    private final ImmutableSet.Builder<CallEvent> callEvents;


    // todo: relations

    public ProcessusBuilder(String name) {
        this.name = name;
        memoryEvents = new ImmutableSet.Builder<>(); // todo: initial capacity, load factor ...
        callEvents = new ImmutableSet.Builder<>();
        barrierEvents = new ImmutableSet.Builder<>();
        built = false;
    }

    public Processus build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        setBuilt();
        return new Processus(this);
    }

    public String getName() {
        return name;
    }

    public void addMemoryEvent(MemoryEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        memoryEvents.add(event);
    }

    public void addCallEvent(CallEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        callEvents.add(event);
    }

    public void addBarrierEvent(BarrierEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        barrierEvents.add(event);
    }

    public ImmutableSet<MemoryEvent> getMemoryEvents() {
        if (!isBuilt()) {
            throw new RuntimeException(getNotYetFinishedMessage());
        }
        return memoryEvents.build();
    }

    public ImmutableSet<CallEvent> getCallEvents() {
        if (!isBuilt()) {
            throw new RuntimeException(getNotYetFinishedMessage());
        }
        return callEvents.build();
    }

    public ImmutableSet<BarrierEvent> getBarrierEvents() {
        if (!isBuilt()) {
            throw new RuntimeException(getNotYetFinishedMessage());
        }
        return barrierEvents.build();
    }
}
