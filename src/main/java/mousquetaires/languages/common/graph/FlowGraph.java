package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;


// TODO: generalise these interfaces into abstract classes (+ builders) !
public abstract class FlowGraph<N extends FlowGraphNode> {

    private final N source;
    private final N sink;
    private final ImmutableMap<N, N> edges;
    private final ImmutableMap<N, N> altEdges;

    //public FlowGraph(FlowGraph<N> mother) {
    //    this(mother.source, mother.sink, mother.edges, mother.altEdges);
    //}

    public FlowGraph(N source,
                     N sink,
                     ImmutableMap<N, N> edges,
                     ImmutableMap<N, N> altEdges
                     //ImmutableMap<T, ImmutableSet<T>> edgesReversed
                     ) {
        this.source = source;
        this.sink = sink;
        this.edges = edges;
        this.altEdges = altEdges;
        //this.edgesReversed = edgesReversed; // TODO: also, compute reversed edges while single-pass via info collector
    }

    public N source() {
        return source;
    }

    public N sink() {
        return sink;
    }

    public boolean isSource(N node) {
        return node == source;
    }

    public boolean isSink(N node) {
        return node == sink;
    }

    public N successor(boolean edgeSign, N node) {
        return getEdges(edgeSign).get(node);
    }

    public boolean hasChild(boolean edgeSign, N node) {
        return getEdges(edgeSign).containsKey(node);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlowGraph{");
        sb.append("source=").append(source);
        sb.append(", sink=").append(sink);
        sb.append(", edges=").append(edges);
        sb.append(", altEdges=").append(altEdges);
        sb.append('}');
        return sb.toString();
    }

    // TODO: after debugging, probably remove these methods?
    // ================================================ optional methods (to be removed) ===============================

    public ImmutableMap<N, N> getEdges(boolean edgesSign) {
        return edgesSign ? edges : altEdges;
    }


    public int size() {
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
