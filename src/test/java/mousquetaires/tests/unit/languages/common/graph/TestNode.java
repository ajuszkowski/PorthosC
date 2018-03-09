package mousquetaires.tests.unit.languages.common.graph;

import java.util.Objects;


public class TestNode {
    private final int value;

    public TestNode(int value) {
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
        if (!(o instanceof TestNode)) return false;
        TestNode testNode = (TestNode) o;
        return getValue() == testNode.getValue();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getValue());
    }
}
