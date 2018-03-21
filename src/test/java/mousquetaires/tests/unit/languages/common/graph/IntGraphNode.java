package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.GraphNode;

import java.util.Objects;


public final class IntGraphNode implements GraphNode {
    private static int NON_REFERENCE_ID = 0;

    private final int value;
    private final int referenceId;

    IntGraphNode(int value) {
        this(value, NON_REFERENCE_ID);
    }

    IntGraphNode(IntGraphNode node, int referenceId) {
        this(node.getValue(), referenceId);
    }

    IntGraphNode(int value, int referenceId) {
        this.value = value;
        this.referenceId = referenceId;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getReferenceId() {
        return referenceId;
    }

    @Override
    public String getLabel() {
        return "" + getValue();
    }

    @Override
    public String toString() {
        return "[" + getLabel() + getReferenceIdSuffix() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof IntGraphNode)) { return false; }
        IntGraphNode that = (IntGraphNode) o;
        return getValue() == that.getValue() &&
                getReferenceId() == that.getReferenceId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getValue(), getReferenceId());
    }

    private String getReferenceIdSuffix() {
        return referenceId == NON_REFERENCE_ID
                ? ""
                : "_" + referenceId;
    }
}
