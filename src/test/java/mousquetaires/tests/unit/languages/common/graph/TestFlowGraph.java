package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.Node;


abstract class TestFlowGraph<T extends Node> extends FlowGraph<T> {

    TestFlowGraph(T source,
                  T sink,
                  ImmutableMap<T, T> edges,
                  ImmutableMap<T, T> altEdges,
                  boolean isUnrolled) {
        super(source, sink, edges, altEdges, isUnrolled);
    }
}
