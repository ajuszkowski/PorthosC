package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.GraphNode;

import java.util.Objects;


public class IntGraphNode implements GraphNode {
    private final int value;

    IntGraphNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return "" + getValue();
    }

    @Override
    public String toString() {
        return "[" + getLabel() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntGraphNode)) return false;
        IntGraphNode testNode = (IntGraphNode) o;
        return getValue() == testNode.getValue();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getValue());
    }
}
