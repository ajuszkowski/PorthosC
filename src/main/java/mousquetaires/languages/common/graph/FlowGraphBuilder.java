package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.utils.patterns.Builder;

import java.util.HashMap;
import java.util.Map;


public abstract class FlowGraphBuilder<T extends GraphNode, G extends FlowGraph<T>>
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

    public ImmutableMap<T, T> buildEdges(boolean edgeSign) {
        return ImmutableMap.copyOf(getEdges(edgeSign));
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

    public void addEdge(boolean edgeSign, T from, T to) {
        if (!edgeSign && hasEdge(true, from, to)) {
            return; // do not add 'false' edge that duplicates the 'true' edge
        }
        addEdgeImpl(edgeSign, from, to);
    }

    public void markAsUnrolled() {
        this.isUnrolled = true;
    }

    public boolean hasEdgesFrom(T node) {
        return getEdges(true).containsKey(node) || getEdges(false).containsKey(node);
    }

    public boolean hasEdge(boolean edgeSign, T from, T to) {
        Map<T, T> map = getEdges(edgeSign);
        return map.containsKey(from) && map.get(from).equals(to);
    }

    protected Map<T, T> getEdges(boolean edgeSign) {
        return edgeSign ? edges : altEdges;
    }

    private void addEdgeImpl(boolean edgeSign, T from, T to) {
        assert (from != null) : "attempt to add to graph the null node";
        assert (to != null) : "attempt to add to graph the null node";

        Map<T, T> edgesMap = getEdges(edgeSign);
        Map<T, T> altEdgesMap = getEdges(!edgeSign);

        // TODO: this check is for debug only
        // TODO: remove this after tests are completed
        if (edgesMap.containsKey(from)) {
            T oldTo = edgesMap.get(from);
            if (!oldTo.equals(to)) {
                System.err.println("WARNING: overwriting edge " + from + " -> " + oldTo + " with edge " + from + " -> " + to);
            }
        }

        if (altEdgesMap.containsKey(from) && altEdgesMap.get(from).equals(to)) {
            throw new IllegalArgumentException("Attempt to add " + getEdgeTypeText(edgeSign) + "-edge while already having " +
                    getEdgeTypeText(!edgeSign) + "-edge " + from + " -> " + to);
        }

        edgesMap.put(from, to);
    }

    private String getEdgeTypeText(boolean edgeSign) {
        return (edgeSign) ? "true" : "false";
    }
}