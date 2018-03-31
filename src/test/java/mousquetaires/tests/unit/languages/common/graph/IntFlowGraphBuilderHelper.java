package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.common.graph.FlowGraphNodeInfo;
import mousquetaires.languages.common.graph.IFlowGraphBuilder;


public class IntFlowGraphBuilderHelper {

    // TODO: fix heap pollution here
    public static <N extends FlowGraphNode, G extends FlowGraph<N>>
        void addPath(IFlowGraphBuilder<N, G> builder,
                     boolean edgeSign,
                     N from,
                     N to,
                     N... other) {
        builder.addEdge(edgeSign, from, to);
        N previous = to;
        for (N node : other) {
            builder.addEdge(edgeSign, previous, node);
            previous = node;
        }
    }
}
