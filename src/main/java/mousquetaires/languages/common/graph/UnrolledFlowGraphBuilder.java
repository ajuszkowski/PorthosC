package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.utils.CollectionUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public abstract class UnrolledFlowGraphBuilder<N extends GraphNode, G extends FlowGraph<N>>
        extends FlowGraphBuilder<N, G> {

    private Map<Integer, Set<N>> layers; // TODO: need new builder for test unrolled graph

    private Map<N, Set<N>> reversedEdges;
    private Map<N, Set<N>> altReversedEdges;

    public UnrolledFlowGraphBuilder() {
        this.layers = new HashMap<>();
        this.reversedEdges = new HashMap<>();
        this.altReversedEdges = new HashMap<>();
    }

    public ImmutableMap<N, ImmutableSet<N>> buildReversedEdges(boolean edgeSign) {
        return CollectionUtils.buildMapOfSets(getReversedEdges(edgeSign));
    }

    public ImmutableMap<Integer, UnrolledNodesLayer<N>> buildLayers() {
        ImmutableMap.Builder<Integer, UnrolledNodesLayer<N>> builder = new ImmutableMap.Builder<>();
        for (Map.Entry<Integer, Set<N>> pair : layers.entrySet()) {
            int depth = pair.getKey();
            ImmutableSet<N> nodes = ImmutableSet.copyOf(pair.getValue());
            UnrolledNodesLayer<N> layer = new UnrolledNodesLayer<>(depth, nodes);
            builder.put(depth, layer);
        }
        return builder.build();
    }

    @Override
    public void setSource(N source) {
        super.setSource(source);
        addToLayers(source);
    }

    @Override
    public void addEdge(boolean edgeSign, N from, N to) {
        super.addEdge(edgeSign, from, to);
        this.addReversedEdgeImpl(edgeSign, to, from);
        addToLayers(to);
    }

    protected Map<N, Set<N>> getReversedEdges(boolean edgeSign) {
        return edgeSign ? reversedEdges : altReversedEdges;
    }

    private void addReversedEdgeImpl(boolean edgeSign, N child, N parent) {
        Map<N, Set<N>> reversedEdgesMap = getReversedEdges(edgeSign);
        if (!reversedEdgesMap.containsKey(child)) {
            reversedEdgesMap.put(child, new HashSet<>());
        }
        reversedEdgesMap.get(child).add(parent);
    }

    private void addToLayers(N node) {
        int depth = node.getReferenceId();
        if (!layers.containsKey(depth)) {
            layers.put(depth, new HashSet<>());
        }
        layers.get(depth).add(node);
    }
}