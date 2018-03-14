package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.GraphNode;

import java.util.Objects;


public class IntTestNode implements GraphNode {
    private final int value;

    public IntTestNode(int value) {
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
        if (!(o instanceof IntTestNode)) return false;
        IntTestNode testNode = (IntTestNode) o;
        return getValue() == testNode.getValue();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getValue());
    }
}