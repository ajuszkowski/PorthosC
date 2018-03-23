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
        private final ImmutableSortedMap<Integer, UnrolledNodesLayer<N>> nodesLayers;
        private int currentLayerNumber;
        private Iterator<N> currentLayerIterator;

        public LinearisationIterator(ImmutableSortedMap<Integer, UnrolledNodesLayer<N>> nodesLayers) {
            this.nodesLayers = nodesLayers;
            this.currentLayerNumber = 0;
            this.currentLayerIterator = nodesLayers.firstEntry().getValue().getNodesIterator();
        }

        @Override
        public boolean hasNext() {
            if (currentLayerIterator.hasNext()) {
                return true;
            }
            currentLayerNumber++;
            if (!nodesLayers.containsKey(currentLayerNumber)) {
                return false;
            }
            UnrolledNodesLayer<N> layer = nodesLayers.get(currentLayerNumber);
            currentLayerIterator = layer.getNodesIterator();
            if (!currentLayerIterator.hasNext()) {
                throw new IllegalStateException("layers must have at least single node. The layer " +
                                                        layer.getDepth() + " does not have any.");
            }
            return true;
        }

        @Override
        public N next() {
            return currentLayerIterator.next();
        }
    }
}
