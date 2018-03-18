package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;


public class IntFlowGraph extends FlowGraph<IntGraphNode> {

    public IntFlowGraph(IntGraphNode source,
                        IntGraphNode sink,
                        ImmutableMap<IntGraphNode, IntGraphNode> edges,
                        ImmutableMap<IntGraphNode, IntGraphNode> altEdges) {
        super(source, sink, edges, altEdges);
    }
}
