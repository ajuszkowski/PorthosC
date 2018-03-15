package mousquetaires.tests.unit.languages.common.graph;

public class IntTestFlowGraphBuilder extends TestFlowGraphBuilderBase<IntNode, IntTestFlowGraph> {

    public IntTestFlowGraphBuilder(IntNode source, IntNode sink) {
        setSource(source);
        setSink(sink);
    }

    @Override
    public IntTestFlowGraph build() {
        finishBuilding();
        return new IntTestFlowGraph(getSource(), getSink(), buildEdges(true), buildEdges(false), isUnrolled());
    }

    public static IntNode node(int value) {
        return new IntNode(value);
    }

    public static IntNodeRef r(int value, int index) {
        return new IntNodeRef(value, index);
    }
}
