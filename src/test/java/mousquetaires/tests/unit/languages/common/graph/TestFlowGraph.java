package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;


public class TestFlowGraph extends FlowGraph<TestNode> {

    public TestFlowGraph(TestNode source,
                         TestNode sink,
                         ImmutableMap<TestNode, TestNode> edges,
                         ImmutableMap<TestNode, TestNode> altEdges,
                         boolean isUnrolled) {
        super(source, sink, edges, altEdges, isUnrolled);
    }
}
