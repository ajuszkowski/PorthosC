package porthosc.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import porthosc.languages.common.graph.FlowGraph;


public class IntFlowGraph extends FlowGraph<IntNode> {

    public IntFlowGraph(IntNode source,
                        IntNode sink,
                        ImmutableMap<IntNode, IntNode> edges,
                        ImmutableMap<IntNode, IntNode> altEdges) {
        super(source, sink, edges, altEdges);
    }
}
