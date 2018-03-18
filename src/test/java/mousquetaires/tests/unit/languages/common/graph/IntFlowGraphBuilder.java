package mousquetaires.tests.unit.languages.common.graph;

public class IntFlowGraphBuilder extends TestFlowGraphBuilderBase<IntGraphNode, IntFlowGraph> {

    public IntFlowGraphBuilder(IntGraphNode source, IntGraphNode sink) {
        super(source, sink);
    }

    @Override
    public IntFlowGraph build() {
        finishBuilding();
        return new IntFlowGraph(getSource(), getSink(), buildEdges(true), buildEdges(false));
    }
}
