package mousquetaires.tests.unit.languages.common.graph;

public class IntGraphHelper {

    public static IntFlowGraphNode node(int value) {
        return new IntFlowGraphNode(value);
    }

    public static IntFlowGraphNode r(int value, int index) {
        return new IntFlowGraphNode(value, index);
    }
}
