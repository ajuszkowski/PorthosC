package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;

import java.util.Iterator;


public abstract class UnrolledFlowGraph<N extends GraphNode> extends FlowGraph<N> {

    private final ImmutableSortedMap<Integer, UnrolledNodesLayer<N>> layers; // TODO: need new builder for test unrolled graph

    private ImmutableMap<N, ImmutableSet<N>> reversedEdges;
    private ImmutableMap<N, ImmutableSet<N>> altReversedEdges;

    public UnrolledFlowGraph(N source,
                             N sink,
                             ImmutableMap<N, N> edges,
                             ImmutableMap<N, N> altEdges,
                             ImmutableMap<N, ImmutableSet<N>> reversedEdges,
                             ImmutableMap<N, ImmutableSet<N>> altReversedEdges,
                             ImmutableSortedMap<Integer, UnrolledNodesLayer<N>> layers) {
        super(source, sink, edges, altEdges);
        this.reversedEdges = reversedEdges;
        this.altReversedEdges = altReversedEdges;
        this.layers = layers;
    }

    public Iterator<N> layersIterator() {
        return new LinearisationIterator(layers);
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

    public class LinearisationIterator implements Iterator<N> {
        private Iterator<UnrolledNodesLayer<N>> layersIterator;
        private Iterator<N> currentLayerIterator;

        LinearisationIterator(ImmutableSortedMap<Integer, UnrolledNodesLayer<N>> nodesLayers) {
            this.layersIterator = nodesLayers.values().iterator();
            if (!this.layersIterator.hasNext()) {
                throw new IllegalStateException("graph must have at least single layer");
            }
            this.currentLayerIterator = this.layersIterator.next().getNodesIterator();
        }

        @Override
        public boolean hasNext() {
            if (currentLayerIterator.hasNext()) {
                return true;
            }
            if (!layersIterator.hasNext()) {
                return false;
            }
            UnrolledNodesLayer<N> nextLayer = layersIterator.next();
            currentLayerIterator = nextLayer.getNodesIterator();
            if (!currentLayerIterator.hasNext()) {
                throw new IllegalStateException("layers must have at least single node. The layer " +
                                                        nextLayer + " does not have any");
            }
            return true;
        }

        @Override
        public N next() {
            return currentLayerIterator.next();
        }
    }
}
