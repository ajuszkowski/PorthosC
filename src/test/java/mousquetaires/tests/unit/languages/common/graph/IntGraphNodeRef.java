package mousquetaires.tests.unit.languages.common.graph;

import java.util.Objects;


public class IntGraphNodeRef extends IntGraphNode {
    private final int index;

    public IntGraphNodeRef(int value, int index) {
        this(new IntGraphNode(value), index);
    }

    public IntGraphNodeRef(IntGraphNode node, int index) {
        super(node.getValue());
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String getLabel() {
        return super.getLabel() + "_" + getIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntGraphNodeRef)) return false;
        if (!super.equals(o)) return false;
        IntGraphNodeRef that = (IntGraphNodeRef) o;
        return getIndex() == that.getIndex();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getIndex());
    }

    @Override
    public String toString() {
        return "[" + getLabel() + "]";
    }
}
