package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.Node;

import java.util.Objects;


public class IntNode implements Node {
    private final int value;

    IntNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntNode)) return false;
        IntNode testNode = (IntNode) o;
        return getValue() == testNode.getValue();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getValue());
    }
}
