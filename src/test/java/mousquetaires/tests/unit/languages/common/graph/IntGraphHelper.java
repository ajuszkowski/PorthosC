package mousquetaires.tests.unit.languages.common.graph;

public class IntGraphHelper {

    public static IntGraphNode node(int value) {
        return new IntGraphNode(value);
    }

    public static IntGraphNode r(int value, int index) {
        return new IntGraphNode(value, index);
    }
}
