package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Iterator;


public abstract class UnrolledFlowGraph<N extends FlowGraphNode> extends FlowGraph<N> {

    private final ImmutableList<N> nodesLinearised; // TODO: need new builder for test unrolled graph

    private ImmutableMap<N, ImmutableSet<N>> reversedEdges;
    private ImmutableMap<N, ImmutableSet<N>> altReversedEdges;

    public UnrolledFlowGraph(N source,
                             N sink,
                             ImmutableMap<N, N> edges,
                             ImmutableMap<N, N> altEdges,
                             ImmutableMap<N, ImmutableSet<N>> reversedEdges,
                             ImmutableMap<N, ImmutableSet<N>> altReversedEdges,
                             ImmutableList<N> nodesLinearised) {
        super(source, sink, edges, altEdges);
        this.reversedEdges = reversedEdges;
        this.altReversedEdges = altReversedEdges;
        this.nodesLinearised = nodesLinearised;
    }

    public Iterator<N> linearisedNodesIterator() {
        return nodesLinearised.iterator();
    }

    public boolean hasParent(boolean edgeSign, N node) {
        return getReversedEdges(edgeSign).containsKey(node);
    }

    public ImmutableSet<N> parents(boolean edgeKind, N node) {
        ImmutableMap<N, ImmutableSet<N>> reversedMap = getReversedEdges(edgeKind);
        assert reversedMap.containsKey(node) : node;
        return reversedMap.get(node);
    }

    public ImmutableMap<N, ImmutableSet<N>> getReversedEdges(boolean edgesSign) {
        return edgesSign ? reversedEdges : altReversedEdges;
    }
}
