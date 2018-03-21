package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.traverse.FlowGraphDfsTraverser;


public class IntFlowGraphUnroller extends FlowGraphDfsTraverser<IntGraphNode, UnrolledIntFlowGraph> {

    // TODO: pass settings structure: bound, flags which agents to use

    public IntFlowGraphUnroller(IntFlowGraph graph, int unrollingBound, IntGraphNode newSource, IntGraphNode newSink) {
        super(graph, new UnrolledIntFlowGraphBuilder(newSource, newSink), IntGraphNode::new, unrollingBound);
    }
}
