package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.utils.patterns.Builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public abstract class FlowGraphBuilder<T extends GraphNode> extends Builder<FlowGraph<T>> {

    private final String processId;

    private T source;
    private T sink;

    protected final Map<T, T> edges;
    protected final Map<T, T> altEdges;

    private boolean isUnrolled;

    private HashSet<T> leaves = new HashSet<>();

    public FlowGraphBuilder(String processId) {
        this.processId = processId;
        this.edges = new HashMap<>();
        this.altEdges = new HashMap<>();
        this.isUnrolled = false;
    }

    public String getProcessId() {
        return processId;
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
        markFinished();
        assert source != null : "source node is set";
        assert sink != null : "sink node is set";
        for (T leaf : leaves) {
            addEdge(leaf, sink);
        }
        leaves = null;
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

    public void addAlternativeEdge(T from, T to) {
        addEdgeImpl(altEdges, from, to);
    }

    public void markAsUnrolled() {
        this.isUnrolled = true;
    }

    private void preprocessNewEdge(T from, T to) {
        if (leaves.contains(from)) {
            leaves.remove(from);
        }
        if (!edges.containsKey(to)) {
            leaves.add(to);
        }
    }

    private void addEdgeImpl(Map<T, T> edges, T from, T to) {
        preprocessNewEdge(from, to);
        assert (from != null) : "attempt to add to graph the null node";
        assert (to != null)   : "attempt to add to graph the null node";

        // TODO: this check is for debug only
        if (edges.containsKey(from)) {
            T oldTo = edges.get(from);
            if (!oldTo.equals(to)) { // TODO: remove this // && !(oldTo instanceof XFakeEvent) || (to instanceof XFakeEvent)) {
                System.out.println("WARNING: overwriting edge " + from + " -> " + oldTo + " with edge " + from + " -> " + to);
            }
        }

        edges.put(from, to);
    }
}