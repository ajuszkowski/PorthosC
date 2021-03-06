package porthosc.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import porthosc.languages.common.graph.UnrolledFlowGraph;


public class UnrolledIntFlowGraph extends UnrolledFlowGraph<IntNode> {

    UnrolledIntFlowGraph(IntNode source,
                         IntNode sink,
                         ImmutableMap<IntNode, IntNode> edges,
                         ImmutableMap<IntNode, IntNode> altEdges,
                         ImmutableMap<IntNode, ImmutableSet<IntNode>> edgesReversed,
                         ImmutableMap<IntNode, ImmutableSet<IntNode>> altEdgesReversed,
                         ImmutableList<IntNode> nodesLinearised,
                         ImmutableMap<IntNode, Integer> condLevelMap) {
        super(source, sink, edges, altEdges, edgesReversed, altEdgesReversed, nodesLinearised, condLevelMap);
    }
}
