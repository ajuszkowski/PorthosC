package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;


public class PredecessorCollectorDfsActor<N extends GraphNode, G extends UnrolledFlowGraph<N>>
        implements FlowGraphDfsActor<N, G> {

    private final UnrolledFlowGraphBuilder<N, G> builder;

    public PredecessorCollectorDfsActor(UnrolledFlowGraphBuilder<N, G> builder) {
        this.builder = builder;
    }

    @Override
    public void onEdgeVisit(boolean edgeKind, N from, N to) {
        builder.addPredecessorPair(to, from);
    }
}
