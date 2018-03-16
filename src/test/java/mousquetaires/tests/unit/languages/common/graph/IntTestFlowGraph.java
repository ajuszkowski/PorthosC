package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;


public class IntTestFlowGraph extends TestFlowGraph<IntGraphNode> {

    public IntTestFlowGraph(IntGraphNode source,
                            IntGraphNode sink,
                            ImmutableMap<IntGraphNode, IntGraphNode> edges,
                            ImmutableMap<IntGraphNode, IntGraphNode> altEdges,
                            boolean isUnrolled) {
        super(source, sink, edges, altEdges, isUnrolled);
    }
}
