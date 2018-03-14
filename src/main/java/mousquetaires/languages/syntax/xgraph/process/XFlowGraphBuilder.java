package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XFakeEvent;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class XFlowGraphBuilder extends FlowGraphBuilder<XEvent> {
    private final String processId;
    private XEntryEvent entryEvent;
    private XExitEvent exitEvent;
    private HashMap<XEvent, XEvent> edges; // TODO: immutable map? check this
    private Map<XEvent, Set<XEvent>> edgesReversed;
    private Map<XEvent, Set<XEvent>> altEdgesReversed;
    private HashMap<XEvent/*XComputationEvent*/, XEvent> altEdges;
    //private ImmutableList.Builder<XEvent> linearisedNodes;
    private boolean isUnrolled;

    private HashSet<XEvent> leaves = new HashSet<>();

    public XFlowGraphBuilder(String processId) {
        this.processId = processId;
        this.edges = new HashMap<>();
        this.altEdges = new HashMap<>();
        this.edgesReversed = new HashMap<>();
        this.altEdgesReversed = new HashMap<>();
        //this.linearisedNodes = new ImmutableList.Builder<>();
    }


    //@Override
    //public void addTopologicallyNextNode(XEvent node) {
    //    linearisedNodes.add(node);
    //}

    @Override
    public void markAsUnrolled() {
        isUnrolled = true;
    }

    @Override
    public void finish() {
        // todo: final verifications
        assert entryEvent != null;
        assert exitEvent != null;
        edgesReversed.put(exitEvent, leaves);
        for (XEvent leaf : leaves) {
            edges.put(leaf, exitEvent);
        }
    }

    @Override
    //public void setEntryEvent(XEntryEvent event) {
    public void setSource(XEvent source) {
        assert entryEvent == null: entryEvent;
        if (!(source instanceof XEntryEvent)) {
            throw new IllegalArgumentException("Source event may be only of type " + XEntryEvent.class.getSimpleName() +
                " but received: " + source.getClass().getSimpleName());
        }
        entryEvent = (XEntryEvent) source;
    }

    @Override
    public void setSink(XEvent sink) {
        assert exitEvent == null;
        if (!(sink instanceof XExitEvent)) {
            throw new IllegalArgumentException("Sink event may be only of type " + XExitEvent.class.getSimpleName() +
                    " but received: " + sink.getClass().getSimpleName());
        }
        exitEvent = (XExitEvent) sink;
        assert edges.containsValue(exitEvent) || altEdges.containsValue(exitEvent);
    }

    @Override
    public void addEdge(XEvent from, XEvent to) {
        addEdgeImpl(from, to, edges, edgesReversed);
    }

    @Override
    public void addAlternativeEdge(XEvent from, XEvent to) {
        // TODO: normal checks on type of conditional event 'from'
        if (!(from instanceof XComputationEvent)) {
            throw new IllegalArgumentException("Conditional edge may outgo only from " + XComputationEvent.class.getSimpleName() +
                    " but received: " + from.getClass().getSimpleName());
        }
        //XComputationEvent computationFrom = (XComputationEvent) from;
        addEdgeImpl(from, to, altEdges, altEdgesReversed);
    }

    // TODO  : inefficient now
    public void replaceEvent(XFakeEvent fakeEvent, XEvent replaceWithEvent) {
        boolean replaced = replaceEventImpl(fakeEvent, replaceWithEvent, edges, edgesReversed);
        boolean altReplaced = replaceEventImpl(fakeEvent, replaceWithEvent, altEdges, altEdgesReversed);
        // TODO: check that the second call is not removed by java optimiser
        assert replaced || altReplaced: "could not find any predecessor for continueing event " + StringUtils.wrap(fakeEvent);
    }

    @Override
    public XFlowGraph build() {
        return new XFlowGraph(processId,
                entryEvent,
                exitEvent,
                ImmutableMap.copyOf(edges),
                ImmutableMap.copyOf(altEdges),
                CollectionUtils.buildMapOfSets(edgesReversed),
                //linearisedNodes.build(),
                isUnrolled);
    }


    // TODO: REMOVE THIS METHOD, CHECK BY SEPARATE VISITOR, E.G., IN UNROLLER!!
    public boolean verifyGraph() {
        //for (XComputationEvent event : ifTrueJumps.keySet()) {
        //    assert altEdges.containsKey(event) : "then without else: " + event;
        //}
        //for (XComputationEvent event : altEdges.keySet()) {
        //    assert ifTrueJumps.containsKey(event) : "else without then: " + event;
        //    assert !edges.containsKey(event) : "both conditional-jump and next-jump for " + event;
        //}
        //for (XEvent event : edges.keySet()) {
        //    if (event instanceof XComputationEvent) {
        //        XComputationEvent computEvent = (XComputationEvent) event;
        //        assert !ifTrueJumps.containsKey(computEvent) : "both next-jump and conditional-jump for condition: " + computEvent;
        //        assert !altEdges.containsKey(computEvent) : "both next-jump and conditional-jump for condition: " + computEvent;
        //    }
        //    else if (event instanceof XExitEvent) {
        //        assert !edges.containsValue(event) : "next-jump out of exit event";
        //        assert !ifTrueJumps.containsValue(event) : "then-jump out of exit event";
        //        assert !altEdges.containsValue(event) : "else-jump out of exit event";
        //    }
        //}

        return true;
    }

    private void verifyEvent(XEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Null event");
        }
        // TODO: verify that graph is connected  by remembering leaves (and dangling nodes) on each step
    }

    private void addEdgeImpl(XEvent from,
                             XEvent to,
                             Map<XEvent, XEvent> map,
                             Map<XEvent, Set<XEvent>> mapReversed) {
        verifyEvent(from);
        verifyEvent(to);
        if (leaves.contains(from)) {
            leaves.remove(from);
        }
        if (!edges.containsKey(to) && !altEdges.containsKey(to)) {
            leaves.add(to);
        }

        if (map.containsKey(from)) {
            XEvent oldTo = map.get(from);
            if (!oldTo.equals(to) && !(oldTo instanceof XFakeEvent) || (to instanceof XFakeEvent)) {
                System.out.println("WARNING: overwriting edge " + from + " -> " + oldTo + " with edge " + from + " -> " + to);
            }
        }
        map.put(from, to);
        if (!mapReversed.containsKey(to)) {
            mapReversed.put(to, new HashSet<>());
        }
        else {
            assert !mapReversed.get(to).contains(from) : "adding duplicating reverse-edge: " + from + " -> " + to;
        }
        mapReversed.get(to).add(from);
    }

    private boolean replaceEventImpl(XFakeEvent fakeEvent,
                                     XEvent replacement,
                                     Map<XEvent, XEvent> map,
                                     Map<XEvent, Set<XEvent>> mapReversed) {
        boolean removed = false;
        if (map.containsKey(fakeEvent)) {
            assert mapReversed.containsKey(fakeEvent) : "graph is disconnected";
            map.remove(fakeEvent);
            Set<XEvent> fromSet = mapReversed.remove(fakeEvent);
            for (XEvent from : fromSet) {
                if (!hasEdge(from, replacement, map, mapReversed)) {
                    addEdgeImpl(from, replacement, map, mapReversed);
                    removed = true;
                }
            }
        }
        return removed;
    }

    private boolean hasEdge(XEvent from,
                            XEvent to,
                            Map<XEvent, XEvent> map,
                            Map<XEvent, Set<XEvent>> mapReversed) {
        if (map.containsKey(from)) {
            if (map.get(from).equals(to)) {
                assert mapReversed.containsKey(to) && mapReversed.get(to).contains(from);
                return true;
            }
        }
        return false;
    }

    // TODO: wrap pairs map-mapReversed (for edges and reverse edges) into single class here and add methods hasEdge, replaceEvent, etc...
}
