package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;


public class IntFlowGraph extends FlowGraph<IntFlowGraphNode> {

    public IntFlowGraph(IntFlowGraphNode source,
                        IntFlowGraphNode sink,
                        ImmutableMap<IntFlowGraphNode, IntFlowGraphNode> edges,
                        ImmutableMap<IntFlowGraphNode, IntFlowGraphNode> altEdges) {
        super(source, sink, edges, altEdges);
    }
}
