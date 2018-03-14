package mousquetaires.tests.unit.languages.common.graph;

import java.util.Objects;


public class IntTestNodeRef extends IntTestNode {
    private final int refId;

    public IntTestNodeRef(IntTestNode node, int refId) {
        super(node.getValue());
        this.refId = refId;
    }

    public int getRefId() {
        return refId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntTestNodeRef)) return false;
        if (!super.equals(o)) return false;
        IntTestNodeRef that = (IntTestNodeRef) o;
        return getRefId() == that.getRefId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getRefId());
    }

    @Override
    public String toString() {
        return "[" + getValue() + "_" + getRefId() + "]";
    }
}
