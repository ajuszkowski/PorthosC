package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.traverse.FlowGraphUnrollingActor;
import mousquetaires.languages.common.graph.traverse.FlowGraphUnrollingTraverser;


public class IntTestFlowGraphUnroller extends FlowGraphUnrollingTraverser<IntGraphNode, IntTestFlowGraph> {

    // TODO: pass settings structure: bound, flags which agents to use

    public IntTestFlowGraphUnroller(IntTestFlowGraph graph, int unrollingBound, IntGraphNode source, IntGraphNode sink) {
        super(graph, IntGraphNodeRef::new, unrollingBound, getUnrollingActor(source, sink));
    }

    private static FlowGraphUnrollingActor<IntGraphNode, IntTestFlowGraph> getUnrollingActor(IntGraphNode source, IntGraphNode sink) {
        return new FlowGraphUnrollingActor<>(new IntTestFlowGraphBuilder(source, sink));
    }
}
