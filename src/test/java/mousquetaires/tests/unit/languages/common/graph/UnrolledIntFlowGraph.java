package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledNodesLayer;


public class UnrolledIntFlowGraph extends UnrolledFlowGraph<IntGraphNode> {

    UnrolledIntFlowGraph(IntGraphNode source,
                                IntGraphNode sink,
                                ImmutableMap<IntGraphNode, IntGraphNode> edges,
                                ImmutableMap<IntGraphNode, IntGraphNode> altEdges,
                                ImmutableMap<IntGraphNode, ImmutableSet<IntGraphNode>> edgesReversed,
                                ImmutableMap<IntGraphNode, ImmutableSet<IntGraphNode>> altEdgesReversed,
                                ImmutableMap<Integer, UnrolledNodesLayer<IntGraphNode>> layers) {
        super(source, sink, edges, altEdges, edgesReversed, altEdgesReversed, layers);
    }
}
