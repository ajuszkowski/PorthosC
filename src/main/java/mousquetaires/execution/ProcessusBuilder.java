package mousquetaires.execution;

import com.google.common.collect.ImmutableSet;
import mousquetaires.execution.events.BarrierEvent;
import mousquetaires.execution.events.CallEvent;
import mousquetaires.execution.events.MemoryEvent;
import mousquetaires.patterns.Builder;


public class ProcessusBuilder implements Builder<Processus> {

    private static final String FINISHED_MESSAGE = "Processus building has already built.";
    private static final String NOT_FINISHED_MESSAGE = "Processus building is not built yet.";

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
        if (built) {
            throw new RuntimeException(FINISHED_MESSAGE);
        }
        built = true;
        return new Processus(this);
    }

    public String getName() {
        return name;
    }

    public void addMemoryEvent(MemoryEvent event) {
        if (built) {
            throw new RuntimeException(FINISHED_MESSAGE);
        }
        memoryEvents.add(event);
    }

    public void addCallEvent(CallEvent event) {
        if (built) {
            throw new RuntimeException(FINISHED_MESSAGE);
        }
        callEvents.add(event);
    }

    public void addBarrierEvent(BarrierEvent event) {
        if (built) {
            throw new RuntimeException(FINISHED_MESSAGE);
        }
        barrierEvents.add(event);
    }

    public ImmutableSet<MemoryEvent> getMemoryEvents() {
        if (!built) {
            throw new RuntimeException(NOT_FINISHED_MESSAGE);
        }
        return memoryEvents.build();
    }

    public ImmutableSet<CallEvent> getCallEvents() {
        if (!built) {
            throw new RuntimeException(NOT_FINISHED_MESSAGE);
        }
        return callEvents.build();
    }

    public ImmutableSet<BarrierEvent> getBarrierEvents() {
        if (!built) {
            throw new RuntimeException(NOT_FINISHED_MESSAGE);
        }
        return barrierEvents.build();
    }
}
