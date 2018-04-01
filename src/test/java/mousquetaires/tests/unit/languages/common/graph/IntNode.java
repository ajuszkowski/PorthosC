package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.common.graph.FlowGraphNodeInfo;

import java.util.Objects;


public final class IntNode implements FlowGraphNode<IntNodeInfo> {
    private final IntNodeInfo info;
    private final int value;

    IntNode(int value) {
        this(value, new IntNodeInfo());
    }

    IntNode(int value, IntNodeInfo info) {
        this.info = info;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IntNodeInfo getInfo() {
        return info;
    }

    @Override
    public IntNode withInfo(IntNodeInfo newInfo) {
        return new IntNode(getValue(), newInfo);
    }

    @Override
    public String getName() {
        return "[" + "n=" + value + "," + getInfo() + "]";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof IntNode)) { return false; }
        IntNode intNode = (IntNode) o;
        return getValue() == intNode.getValue() &&
                Objects.equals(getInfo(), intNode.getInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInfo(), getValue());
    }
}
