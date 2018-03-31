package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraphNode;

import java.util.Objects;


public final class IntFlowGraphNode implements FlowGraphNode {
    private final int value;
    private final int referenceId;

    IntFlowGraphNode(int value) {
        this(value, NON_REFERENCE_ID);
    }

    IntFlowGraphNode(IntFlowGraphNode node, int referenceId) {
        this(node.getValue(), referenceId);
    }

    IntFlowGraphNode(int value, int referenceId) {
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
    public String nodeId() {
        return "" + getValue();
    }

    @Override
    public String toString() {
        return "[" + nodeId() + getReferenceIdSuffix() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof IntFlowGraphNode)) { return false; }
        IntFlowGraphNode that = (IntFlowGraphNode) o;
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
