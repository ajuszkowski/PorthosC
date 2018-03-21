package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraphBuilder;


public class IntFlowGraphBuilder extends FlowGraphBuilder<IntGraphNode, IntFlowGraph> {

    public IntFlowGraphBuilder(IntGraphNode source, IntGraphNode sink) {
        setSource(source);
        setSink(sink);
    }

    @Override
    public IntFlowGraph build() {
        finishBuilding();
        return new IntFlowGraph(getSource(), getSink(), buildEdges(true), buildEdges(false));
    }
}
