package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;


public class IntTestFlowGraph extends TestFlowGraph<IntNode> {

    public IntTestFlowGraph(IntNode source,
                            IntNode sink,
                            ImmutableMap<IntNode, IntNode> edges,
                            ImmutableMap<IntNode, IntNode> altEdges,
                            boolean isUnrolled) {
        super(source, sink, edges, altEdges, isUnrolled);
    }
}
