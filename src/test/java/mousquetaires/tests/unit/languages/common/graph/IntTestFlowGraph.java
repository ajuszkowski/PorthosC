package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;


public class IntTestFlowGraph extends TestFlowGraph<IntTestNode> {

    public IntTestFlowGraph(IntTestNode source,
                            IntTestNode sink,
                            ImmutableMap<IntTestNode, IntTestNode> edges,
                            ImmutableMap<IntTestNode, IntTestNode> altEdges,
                            boolean isUnrolled) {
        super(source, sink, edges, altEdges, isUnrolled);
    }
}
