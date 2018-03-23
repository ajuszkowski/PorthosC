package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;


class DfsLinearisationActor<N extends GraphNode, G extends UnrolledFlowGraph<N>>
        extends FlowGraphTraverseActor<N, G> {

    DfsLinearisationActor(UnrolledFlowGraphBuilder<N, G> builder) {
        super(builder);
    }

    @Override
    public void onNodePostVisit(N node) {
        builder.processTopologicallyNextNode(node);
    }
}
