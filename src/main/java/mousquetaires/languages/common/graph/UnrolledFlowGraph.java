package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Iterator;


public abstract class UnrolledFlowGraph<N extends FlowGraphNode> extends FlowGraph<N> {

    private final ImmutableList<N> nodesLinearised; // TODO: need new builder for test unrolled graph

    private ImmutableMap<N, ImmutableSet<N>> edgesReversed;
    private ImmutableMap<N, ImmutableSet<N>> altEdgesReversed;

    public UnrolledFlowGraph(N source,
                             N sink,
                             ImmutableMap<N, N> edges,
                             ImmutableMap<N, N> altEdges,
                             ImmutableMap<N, ImmutableSet<N>> edgesReversed,
                             ImmutableMap<N, ImmutableSet<N>> altEdgesReversed,
                             ImmutableList<N> nodesLinearised) {
        super(source, sink, edges, altEdges);
        this.edgesReversed = edgesReversed;
        this.altEdgesReversed = altEdgesReversed;
        this.nodesLinearised = nodesLinearised;
    }

    public Iterator<N> linearisedNodesIterator() {
        return nodesLinearised.iterator();
    }

    public boolean hasParent(boolean edgeSign, N node) {
        return getEdgesReversed(edgeSign).containsKey(node);
    }

    public ImmutableSet<N> parents(boolean edgeKind, N node) {
        ImmutableMap<N, ImmutableSet<N>> reversedMap = getEdgesReversed(edgeKind);
        assert reversedMap.containsKey(node) : node;
        return reversedMap.get(node);
    }

    public ImmutableMap<N, ImmutableSet<N>> getEdgesReversed(boolean edgesSign) {
        return edgesSign ? edgesReversed : altEdgesReversed;
    }
}
