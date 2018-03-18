package mousquetaires.languages.common.graph;

public interface UnrolledFlowGraphBuilder<N extends GraphNode, G extends UnrolledFlowGraph<N>>
        extends FlowGraphBuilder<N, G> {

    void addNextLinearisedNode(N node);

    void addPredecessorPair(N child, N parent);
}
