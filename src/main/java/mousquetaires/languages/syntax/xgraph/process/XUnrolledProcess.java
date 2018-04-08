package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import sun.reflect.generics.tree.ReturnType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;


public final class XUnrolledProcess extends UnrolledFlowGraph<XEvent> {

    private final XProcessId id;

    // memoisation fields:
    private final Map<Predicate<XEvent>, ImmutableSet<XEvent>> memoisedEvents;

    XUnrolledProcess(XProcessId id,
                     XEvent source, XEvent sink,
                     ImmutableMap<XEvent, XEvent> edges,
                     ImmutableMap<XEvent, XEvent> altEdges,
                     ImmutableMap<XEvent, ImmutableSet<XEvent>> edgesReversed,
                     ImmutableMap<XEvent, ImmutableSet<XEvent>> altEdgesReversed,
                     ImmutableList<XEvent> nodesLinearised) {
        super(source, sink, edges, altEdges, edgesReversed, altEdgesReversed, nodesLinearised);
        this.id = id;
        this.memoisedEvents = new HashMap<>();
    }

    public ImmutableSet<XEvent> getAllEvents() {
        return getEvents(e -> true);
    }

    public ImmutableSet<XEvent> getEvents(Predicate<XEvent> filter) {
        if (memoisedEvents.containsKey(filter)) {
            return memoisedEvents.get(filter);
        }
        ImmutableSet.Builder<XEvent> builder = new ImmutableSet.Builder<>();
        for (XEvent event : getAllEventsExceptEntry()) {
            if (filter.test(event)) {
                builder.add(event);
            }
        }
        ImmutableSet<XEvent> result = builder.build();
        memoisedEvents.put(filter, result);
        return result;
    }

    private ImmutableCollection<XEvent> getAllEventsExceptEntry() {
        return getEdges(true).values();
    }

}
