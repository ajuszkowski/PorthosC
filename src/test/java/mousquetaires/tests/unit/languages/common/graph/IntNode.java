package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraphNode;


public final class IntNode implements FlowGraphNode {

    private final int value;
    private int refId;

    IntNode(int value) {
        this.value = value;
    }

    IntNode(int value, int refId) {
        this.value = value;
        this.refId = refId;
    }

    @Override
    public int getUniqueId() {
        return value;
    }

    @Override
    public int getRefId() {
        return refId;
    }

    @Override
    public String getName() {
        return "[" + "n=" + getUniqueId() + ",d=" + getRefId() + "]";
    }

    @Override
    public String toString() {
        return getName();
    }
}
