package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;


public final class XUnrolledProcess extends UnrolledFlowGraph<XEvent> {

    private final XProcessId id;

    // memoisation fields:
    private ImmutableSet<XEvent> allEvents;
    private ImmutableSet<XBarrierEvent> barrierEvents;
    private ImmutableSet<XLoadMemoryEvent> loadEvents;
    private ImmutableSet<XStoreMemoryEvent> storeEvents;

    XUnrolledProcess(XProcessId id,
                     XEvent source, XEvent sink,
                     ImmutableMap<XEvent, XEvent> edges,
                     ImmutableMap<XEvent, XEvent> altEdges,
                     ImmutableMap<XEvent, ImmutableSet<XEvent>> edgesReversed,
                     ImmutableMap<XEvent, ImmutableSet<XEvent>> altEdgesReversed,
                     ImmutableList<XEvent> nodesLinearised) {
        super(source, sink, edges, altEdges, edgesReversed, altEdgesReversed, nodesLinearised);
        this.id = id;
    }

    // TODO: getInitEvents()

    public ImmutableSet<XEvent> getAllEvents() {
        if (allEvents == null) {
            computeMemoisedEvents();
        }
        return allEvents;
    }

    public ImmutableSet<XBarrierEvent> getBarrierEvents() {
        if (barrierEvents == null) {
            computeMemoisedEvents();
        }
        return barrierEvents;
    }

    public ImmutableSet<XLoadMemoryEvent> getLoadEvents() {
        if (loadEvents == null) {
            computeMemoisedEvents();
        }
        return loadEvents;
    }

    public ImmutableSet<XStoreMemoryEvent> getStoreEvents() {
        if (storeEvents == null) {
            computeMemoisedEvents();
        }
        return storeEvents;
    }

    public XProcessId getId() {
        return id;
    }

    private void computeMemoisedEvents() {
        //false-edges must be already in the set
        ImmutableSet.Builder<XEvent> allEventsBuilder = new ImmutableSet.Builder<>();
        ImmutableSet.Builder<XBarrierEvent> barrierEventsBuilder = new ImmutableSet.Builder<>();
        ImmutableSet.Builder<XLoadMemoryEvent> loadEventsBuilder = new ImmutableSet.Builder<>();
        ImmutableSet.Builder<XStoreMemoryEvent> storeEventsBuilder = new ImmutableSet.Builder<>();

        allEventsBuilder.add(source());
        for (XEvent event : getEdges(true).values()) {
            allEventsBuilder.add(event);
            if (event instanceof XBarrierEvent) {
                barrierEventsBuilder.add((XBarrierEvent) event);
            }
            else if (event instanceof XLoadMemoryEvent) {
                loadEventsBuilder.add((XLoadMemoryEvent) event);
            }
            else if (event instanceof XStoreMemoryEvent) {
                storeEventsBuilder.add((XStoreMemoryEvent) event);
            }
        }

        allEvents = allEventsBuilder.build();
        barrierEvents = barrierEventsBuilder.build();
        loadEvents = loadEventsBuilder.build();
        storeEvents = storeEventsBuilder.build();
    }


}
