package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;


public class UnrolledIntFlowGraph extends UnrolledFlowGraph<IntNode> {

    UnrolledIntFlowGraph(IntNode source,
                         IntNode sink,
                         ImmutableMap<IntNode, IntNode> edges,
                         ImmutableMap<IntNode, IntNode> altEdges,
                         ImmutableMap<IntNode, ImmutableSet<IntNode>> edgesReversed,
                         ImmutableMap<IntNode, ImmutableSet<IntNode>> altEdgesReversed,
                         ImmutableList<IntNode> layers) {
        super(source, sink, edges, altEdges, edgesReversed, altEdgesReversed, layers);
    }
}
