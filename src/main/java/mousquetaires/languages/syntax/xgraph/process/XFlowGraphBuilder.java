package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fakes.XFakeEvent;
import mousquetaires.utils.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class XFlowGraphBuilder extends FlowGraphBuilder<XEvent> {
    private final String processId;
    private XEntryEvent entryEvent;
    private XExitEvent exitEvent;
    private final HashMap<XEvent, XEvent> edges; // TODO: immutable map? check this
    // TODO: merge edges and ifTrueJumps into one hashmap
    private final HashMap<XEvent/*XComputationEvent*/, XEvent> alternativeEdges;
    private boolean isUnrolled = false;

    private HashSet<XEvent> leaves = new HashSet<>();

    public XFlowGraphBuilder(String processId) {
        this.processId = processId;
        this.edges = new HashMap<>();
        this.alternativeEdges = new HashMap<>();
    }

    public void markAsUnrolled() {
        isUnrolled = true;
    }

    @Override
    public void finish() {
        // todo: final verifications
        assert entryEvent != null;
        assert exitEvent != null;
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
        //assert edges.containsValue(exitEvent) || alternativeEdges.containsValue(exitEvent);
    }

    @Override
    //public void addLinearEdge(XEvent from, XEvent to) {
    public void addEdge(XEvent from, XEvent to) {
        verifyEvent(from);
        verifyEvent(to);
        processLeaf(from, to);
        edges.put(from, to);
    }

    @Override
    //public void addElseEdge(XComputationEvent from, XEvent to) {
    public void addAlternativeEdge(XEvent from, XEvent to) {
        // TODO: normal checks on type of conditional event 'from'
        //if (!(from instanceof XComputationEvent)) {
        //    throw new IllegalArgumentException("Conditional edge may outgo only from " + XComputationEvent.class.getSimpleName() +
        //            " but received: " + from.getClass().getSimpleName());
        //}
        //XComputationEvent computationFrom = (XComputationEvent) from;
        verifyEvent(from);
        verifyEvent(to);
        processLeaf(from, to);
        alternativeEdges.put(from/*computationFrom*/, to);
    }

    // TODO  : inefficient now
    public void replaceEvent(XFakeEvent fakeEvent, XEvent replaceWithEvent) {
        XEvent oldDestination = edges.remove(fakeEvent);
        // TODO : replace old destination also

        boolean removed = false;
        Set<XEvent> linearPredecessors          = Maps.filterValues(edges, succ -> Objects.equals(succ, fakeEvent)).keySet();
        Set<XEvent> elsePredecessors = Maps.filterValues(alternativeEdges, succ -> Objects.equals(succ, fakeEvent)).keySet();

        for (XEvent linearPredecessor : linearPredecessors) {
            addEdge(linearPredecessor, replaceWithEvent);
            removed = true;
        }
        for (XEvent elsePredecessor : elsePredecessors) {
            addAlternativeEdge(elsePredecessor, replaceWithEvent);
            removed = true;
        }
        assert removed: "could not find any predecessor for continueing event " + StringUtils.wrap(fakeEvent);
    }

    @Override
    public XFlowGraph build() {
        return new XFlowGraph(processId,
                entryEvent,
                exitEvent,
                ImmutableMap.copyOf(edges),
                ImmutableMap.copyOf(alternativeEdges),
                isUnrolled);
    }
    

    // TODO: REMOVE THIS METHOD, CHECK BY SEPARATE VISITOR, E.G., IN UNROLLER!!
    public boolean verifyGraph() {
        //for (XComputationEvent event : ifTrueJumps.keySet()) {
        //    assert alternativeEdges.containsKey(event) : "then without else: " + event;
        //}
        //for (XComputationEvent event : alternativeEdges.keySet()) {
        //    assert ifTrueJumps.containsKey(event) : "else without then: " + event;
        //    assert !edges.containsKey(event) : "both conditional-jump and next-jump for " + event;
        //}
        //for (XEvent event : edges.keySet()) {
        //    if (event instanceof XComputationEvent) {
        //        XComputationEvent computEvent = (XComputationEvent) event;
        //        assert !ifTrueJumps.containsKey(computEvent) : "both next-jump and conditional-jump for condition: " + computEvent;
        //        assert !alternativeEdges.containsKey(computEvent) : "both next-jump and conditional-jump for condition: " + computEvent;
        //    }
        //    else if (event instanceof XExitEvent) {
        //        assert !edges.containsValue(event) : "next-jump out of exit event";
        //        assert !ifTrueJumps.containsValue(event) : "then-jump out of exit event";
        //        assert !alternativeEdges.containsValue(event) : "else-jump out of exit event";
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

    private void processLeaf(XEvent from, XEvent to) {
        if (leaves.contains(from)) {
            leaves.remove(from);
        }
        if (!edges.containsKey(to) && !alternativeEdges.containsKey(to)) {
            leaves.add(to);
        }
    }
}
