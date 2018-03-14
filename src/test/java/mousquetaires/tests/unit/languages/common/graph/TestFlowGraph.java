package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.GraphNode;


public class TestFlowGraph<T extends GraphNode> extends FlowGraph<T> {

    public TestFlowGraph(T source,
                         T sink,
                         ImmutableMap<T, T> edges,
                         ImmutableMap<T, T> altEdges,
                         boolean isUnrolled) {
        super(source, sink, edges, altEdges, isUnrolled);
    }
}
