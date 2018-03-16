package mousquetaires.tests.unit.languages.common.graph;

public class IntTestFlowGraphBuilder extends TestFlowGraphBuilderBase<IntGraphNode, IntTestFlowGraph> {

    public IntTestFlowGraphBuilder(IntGraphNode source, IntGraphNode sink) {
        setSource(source);
        setSink(sink);
    }

    @Override
    public IntTestFlowGraph build() {
        finishBuilding();
        return new IntTestFlowGraph(getSource(), getSink(), buildEdges(true), buildEdges(false), isUnrolled());
    }

    public static IntGraphNode node(int value) {
        return new IntGraphNode(value);
    }

    public static IntGraphNodeRef r(int value, int index) {
        return new IntGraphNodeRef(value, index);
    }
}
