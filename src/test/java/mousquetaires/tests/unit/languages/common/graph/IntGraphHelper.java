package mousquetaires.tests.unit.languages.common.graph;

public class IntGraphHelper {

    public static IntNode node(int value) {
        return new IntNode(value);
    }

    public static IntNode r(int value, int depth) {
        return new IntNode(value, depth);
    }
}
