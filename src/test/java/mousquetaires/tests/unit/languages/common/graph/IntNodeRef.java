package mousquetaires.tests.unit.languages.common.graph;

import java.util.Objects;


public class IntNodeRef extends IntNode {
    private final int index;

    public IntNodeRef(int value, int index) {
        this(new IntNode(value), index);
    }

    public IntNodeRef(IntNode node, int index) {
        super(node.getValue());
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntNodeRef)) return false;
        if (!super.equals(o)) return false;
        IntNodeRef that = (IntNodeRef) o;
        return getIndex() == that.getIndex();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getIndex());
    }

    @Override
    public String toString() {
        return "[" + getValue() + "_" + getIndex() + "]";
    }
}
