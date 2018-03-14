package mousquetaires.tests.unit.languages.common.graph;

public class IntTestFlowGraphBuilder extends TestFlowGraphBuilder<IntTestNode, IntTestFlowGraph> {

    public IntTestFlowGraphBuilder(String processId) {
        super(processId, new IntTestNode(0), new IntTestNode(-1));
    }

    @Override
    public IntTestFlowGraph build() {
        finishBuilding();
        return new IntTestFlowGraph(getSource(), getSink(), buildEdges(), buildAltEdges(), isUnrolled());
    }
}
