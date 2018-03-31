package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.common.graph.FlowGraphNodeInfo;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;


abstract class FlowGraphTraverseActor<N extends FlowGraphNode, G extends FlowGraph<N>> {
    protected final UnrolledFlowGraphBuilder<N, G> builder;

    FlowGraphTraverseActor(UnrolledFlowGraphBuilder<N, G> builder) {
        this.builder = builder;
    }

    public void onStart() {
        // do nothing yet
    }

    public void onNodePreVisit(N node) {
        // do nothing yet
    }

    public void onEdgeVisit(boolean edgeKind, N from, N to) {
        // do nothing yet
    }

    public void onNodePostVisit(N node) {
        // do nothing yet
    }

    public void onLastNodeVisit(N lastNode) {
        // do nothing yet
    }

    public void onFinish() {
        // do nothing yet
    }
}
