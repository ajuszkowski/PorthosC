package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;


public class LineariserDfsActor<N extends GraphNode, G extends UnrolledFlowGraph<N>>
        implements FlowGraphDfsActor<N, G> {

    private final UnrolledFlowGraphBuilder<N, G> builder;

    public LineariserDfsActor(UnrolledFlowGraphBuilder<N, G> builder) {
        this.builder = builder;
    }

    @Override
    public void onNodePostVisit(N node) {
        builder.addNextLinearisedNode(node);
    }
}
