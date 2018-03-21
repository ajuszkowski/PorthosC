package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Iterator;


public abstract class UnrolledFlowGraph<N extends GraphNode> extends FlowGraph<N> {

    private final ImmutableMap<Integer, UnrolledNodesLayer<N>> layers; // TODO: need new builder for test unrolled graph

    private ImmutableMap<N, ImmutableSet<N>> reversedEdges;
    private ImmutableMap<N, ImmutableSet<N>> altReversedEdges;

    public UnrolledFlowGraph(N source,
                             N sink,
                             ImmutableMap<N, N> edges,
                             ImmutableMap<N, N> altEdges,
                             ImmutableMap<N, ImmutableSet<N>> reversedEdges,
                             ImmutableMap<N, ImmutableSet<N>> altReversedEdges,
                             ImmutableMap<Integer, UnrolledNodesLayer<N>> layers) {
        super(source, sink, edges, altEdges);
        this.reversedEdges = reversedEdges;
        this.altReversedEdges = altReversedEdges;
        this.layers = layers;
    }

    public Iterator<UnrolledNodesLayer<N>> layersIterator() {

        //return layers.values().iterator(); // TODO: check whether we can iterate multiple times via same iterator layersIterator()
    }

    public ImmutableSet<N> predecessors(boolean edgeKind, N node) {
        ImmutableMap<N, ImmutableSet<N>> reversedMap = getReversedEdges(edgeKind);
        assert reversedMap.containsKey(node);
        return reversedMap.get(node);
    }

    public ImmutableMap<N, ImmutableSet<N>> getReversedEdges(boolean edgesSign) {
        return edgesSign ? reversedEdges : altReversedEdges;
    }
}
