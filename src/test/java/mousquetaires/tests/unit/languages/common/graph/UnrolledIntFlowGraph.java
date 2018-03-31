package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;


public class UnrolledIntFlowGraph extends UnrolledFlowGraph<IntFlowGraphNode> {

    UnrolledIntFlowGraph(IntFlowGraphNode source,
                                IntFlowGraphNode sink,
                                ImmutableMap<IntFlowGraphNode, IntFlowGraphNode> edges,
                                ImmutableMap<IntFlowGraphNode, IntFlowGraphNode> altEdges,
                                ImmutableMap<IntFlowGraphNode, ImmutableSet<IntFlowGraphNode>> edgesReversed,
                                ImmutableMap<IntFlowGraphNode, ImmutableSet<IntFlowGraphNode>> altEdgesReversed,
                                ImmutableList<IntFlowGraphNode> layers) {
        super(source, sink, edges, altEdges, edgesReversed, altEdgesReversed, layers);
    }
}
