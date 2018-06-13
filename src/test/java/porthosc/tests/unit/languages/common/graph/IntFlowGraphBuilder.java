package porthosc.tests.unit.languages.common.graph;

import porthosc.languages.common.graph.FlowGraphBuilder;


public class IntFlowGraphBuilder extends FlowGraphBuilder<IntNode, IntFlowGraph> {

    public IntFlowGraphBuilder(IntNode source, IntNode sink) {
        setSource(source);
        setSink(sink);
    }

    @Override
    public IntFlowGraph build() {
        finishBuilding();
        return new IntFlowGraph(getSource(), getSink(), buildEdges(true), buildEdges(false));
    }
}
