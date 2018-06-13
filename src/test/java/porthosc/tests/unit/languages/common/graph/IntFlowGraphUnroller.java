package porthosc.tests.unit.languages.common.graph;

import porthosc.languages.common.graph.traverse.FlowGraphDfsTraverser;


public class IntFlowGraphUnroller extends FlowGraphDfsTraverser<IntNode, UnrolledIntFlowGraph> {

    // TODO: pass settings structure: bound, flags which agents to use

    public IntFlowGraphUnroller(IntFlowGraph graph, int unrollingBound, IntNode newSource, IntNode newSink) {
        super(graph, new UnrolledIntFlowGraphBuilder(newSource, newSink), unrollingBound);
    }
}
