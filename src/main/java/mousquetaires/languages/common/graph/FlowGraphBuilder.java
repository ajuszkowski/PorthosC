package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.utils.patterns.Builder;

import java.util.HashMap;
import java.util.Map;


public abstract class FlowGraphBuilder<T extends Node, G extends FlowGraph<T>>
        extends Builder<G> {

    protected final Map<T, T> edges;
    protected final Map<T, T> altEdges;
    private T source;
    private T sink;
    private boolean isUnrolled;

    public FlowGraphBuilder() {
        this.edges = new HashMap<>();
        this.altEdges = new HashMap<>();
        this.isUnrolled = false;
    }

    public T getSource() {
        return source;
    }

    public T getSink() {
        return sink;
    }

    public boolean isUnrolled() {
        return isUnrolled;
    }

    public ImmutableMap<T, T> buildEdges() {
        return ImmutableMap.copyOf(edges);
    }

    public ImmutableMap<T, T> buildAltEdges() {
        return ImmutableMap.copyOf(altEdges);
    }

    // --

    public void finishBuilding() {
        // TODO: maybe add some more checks here
        assert getSource() != null : "source node is not set";
        assert getSink() != null : "sink node is not set";
        markFinished();
    }

    public void setSource(T source) {
        assert this.source == null : "source node has already been set";
        this.source = source;
    }

    public void setSink(T sink) {
        assert this.sink == null : "sink node has already been set";
        this.sink = sink;
    }

    public void addEdge(T from, T to) {
        addEdgeImpl(edges, from, to);
    }

    public void addAltEdge(T from, T to) {
        addEdgeImpl(altEdges, from, to);
    }

    public void markAsUnrolled() {
        this.isUnrolled = true;
    }

    protected boolean hasEdgeFrom(T node) {
        return edges.containsKey(node);
    }

    protected boolean hasAltEdgeFrom(T node) {
        return altEdges.containsKey(node);
    }

    protected boolean hasEdge(T from, T to) {
        return hasEdgeFrom(from) && edges.get(from).equals(to);
    }

    protected boolean hasAltEdge(T from, T to) {
        return hasAltEdgeFrom(from) && altEdges.get(from).equals(to);
    }

    private void addEdgeImpl(Map<T, T> edges, T from, T to) {
        assert (from != null) : "attempt to add to graph the null node";
        assert (to != null) : "attempt to add to graph the null node";

        // TODO: this check is for debug only
        // TODO: remove this after tests are completed
        if (edges.containsKey(from)) {
            T oldTo = edges.get(from);
            if (!oldTo.equals(to)) {
                System.err.println("WARNING: overwriting edge " + from + " -> " + oldTo + " with edge " + from + " -> " + to);
            }
        }

        edges.put(from, to);
    }
}