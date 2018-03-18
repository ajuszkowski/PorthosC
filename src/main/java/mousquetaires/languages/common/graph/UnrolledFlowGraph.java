package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;


public abstract class UnrolledFlowGraph<T extends GraphNode> extends FlowGraph<T> {

    private final ImmutableList<T> nodesLinearised; // TODO: need new builder for test unrolled graph
    private final ImmutableMap<T, ImmutableSet<T>> predecessorsMap;

    public UnrolledFlowGraph(T source,
                             T sink,
                             ImmutableMap<T, T> edges,
                             ImmutableMap<T, T> altEdges,
                             ImmutableList<T> nodesLinearised,
                             ImmutableMap<T, ImmutableSet<T>> predecessorsMap) {
        super(source, sink, edges, altEdges);
        this.nodesLinearised = nodesLinearised;
        this.predecessorsMap = predecessorsMap;
    }

    public ImmutableList<T> getNodesLinearised() {
        return nodesLinearised;
    }

    public ImmutableSet<T> predecessors(T node) {
        assert predecessorsMap.containsKey(node) : node + ", " + predecessorsMap;
        return predecessorsMap.get(node);
    }
}
