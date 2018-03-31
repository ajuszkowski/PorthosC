package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.common.graph.FlowGraphNodeInfo;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;


class DfsLinearisationActor<N extends FlowGraphNode, G extends UnrolledFlowGraph<N>>
        extends FlowGraphTraverseActor<N, G> {

    DfsLinearisationActor(UnrolledFlowGraphBuilder<N, G> builder) {
        super(builder);
    }

    @Override
    public void onNodePostVisit(N node) {
        builder.processTopologicallyNextNode(node);
    }
}
