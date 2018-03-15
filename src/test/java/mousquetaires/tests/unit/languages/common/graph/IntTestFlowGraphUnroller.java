package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.traverse.FlowGraphUnrollingActor;
import mousquetaires.languages.common.graph.traverse.FlowGraphUnrollingTraverser;


public class IntTestFlowGraphUnroller extends FlowGraphUnrollingTraverser<IntNode, IntTestFlowGraph> {

    // TODO: pass settings structure: bound, flags which agents to use

    public IntTestFlowGraphUnroller(IntTestFlowGraph graph, int unrollingBound, IntNode source, IntNode sink) {
        super(graph, IntNodeRef::new, unrollingBound, getUnrollingActor(source, sink));
    }

    private static FlowGraphUnrollingActor<IntNode, IntTestFlowGraph> getUnrollingActor(IntNode source, IntNode sink) {
        return new FlowGraphUnrollingActor<>(new IntTestFlowGraphBuilder(source, sink));
    }
}
