package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.utils.CollectionUtils;

import java.util.*;


public abstract class UnrolledFlowGraphBuilder<N extends FlowGraphNode, G extends FlowGraph<N>>
        extends FlowGraphBuilder<N, G> {

    // TODO: need new builder to test unrolled graph
    private Deque<N> linearisationQueue;

    private Map<N, Set<N>> reversedEdges;
    private Map<N, Set<N>> altReversedEdges;

    public UnrolledFlowGraphBuilder() {
        this.linearisationQueue = new LinkedList<>();
        this.reversedEdges = new HashMap<>();
        this.altReversedEdges = new HashMap<>();
    }

    public ImmutableMap<N, ImmutableSet<N>> buildReversedEdges(boolean edgeSign) {
        return CollectionUtils.buildMapOfSets(getReversedEdges(edgeSign));
    }

    public void processTopologicallyNextNode(N node) {
        linearisationQueue.addFirst(node); // todo: addLast ?
    }

    public ImmutableList<N> buildNodesLinearised() {
        return ImmutableList.copyOf(linearisationQueue);
    }

    @Override
    public void finishBuilding() {
        super.finishBuilding();
    }

    @Override
    public void setSource(N source) {
        super.setSource(source);
    }

    @Override
    public void addEdge(boolean edgeSign, N from, N to) {
        super.addEdge(edgeSign, from, to);
        this.addReversedEdgeImpl(edgeSign, to, from);
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
}