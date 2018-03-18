package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;


public class UnrolledIntFlowGraph extends UnrolledFlowGraph<IntGraphNode> {

    public UnrolledIntFlowGraph(IntGraphNode source,
                                IntGraphNode sink,
                                ImmutableMap<IntGraphNode, IntGraphNode> edges,
                                ImmutableMap<IntGraphNode, IntGraphNode> altEdges,
                                ImmutableList<IntGraphNode> nodesLinearised,
                                ImmutableMap<IntGraphNode, ImmutableSet<IntGraphNode>> predecessorsMap) {
        super(source, sink, edges, altEdges, nodesLinearised, predecessorsMap);
    }
}
