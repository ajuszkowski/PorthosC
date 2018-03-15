package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;


// TODO: generalise these interfaces into abstract classes (+ builders) !
public abstract class FlowGraph<T extends Node> {

    private final T source;
    private final T sink;
    private final ImmutableMap<T, T> edges;
    private final ImmutableMap<T, T> altEdges;
    private final boolean isUnrolled;

    public FlowGraph(FlowGraph<T> mother) {
        this(mother.source, mother.sink, mother.edges, mother.altEdges, mother.isUnrolled);
    }

    public FlowGraph(T source,
                     T sink,
                     ImmutableMap<T, T> edges,
                     ImmutableMap<T, T> altEdges,
                     //ImmutableMap<T, ImmutableSet<T>> edgesReversed,
                     boolean isUnrolled) {
        this.source = source;
        this.sink = sink;
        this.edges = edges;
        this.altEdges = altEdges;
        //this.edgesReversed = edgesReversed; // TODO: also, compute reversed edges while single-pass via info collector
        this.isUnrolled = isUnrolled;
    }

    public T source() {
        return source;
    }

    public T sink() {
        return sink;
    }

    public T child(T node) {
        return edges.get(node);
    }

    // TODO: rename to getAltChild()
    public T alternativeChild(T node) {
        return altEdges.get(node);
    }

    public boolean hasChild(T node) {
        return edges.containsKey(node);
    }

    public boolean hasAlternativeChild(T node) {
        return altEdges.containsKey(node);
    }

    public boolean isUnrolled() { //todo: this is computable property of graph!
        return isUnrolled;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlowGraph{");
        sb.append("source=").append(source);
        sb.append(", sink=").append(sink);
        sb.append(", edges=").append(edges);
        sb.append(", altEdges=").append(altEdges);
        sb.append(", isUnrolled=").append(isUnrolled);
        //sb.append(", edgesReversed=").append(edgesReversed);
        sb.append('}');
        return sb.toString();
    }

    // TODO: after debugging, probably remove these methods?
    // ================================================ optional methods (to be removed) ===============================

    public ImmutableMap<T, T> edges() {
        return edges;
    }

    public ImmutableMap<T, T> alternativeEdges() {
        return altEdges;
    }

    public int nodesCount() {
        // todo: count nodes while single-pass via information collector!
        return edges.keySet().size();
    }

    //private final ImmutableMap<T, ImmutableSet<T>> edgesReversed;
    //
    //public ImmutableSet<T> parents(T node) {
    //    if (node.equals(source())) {
    //        return ImmutableSet.of();
    //    }
    //    return edgesReversed.get(node);
    //}

}
