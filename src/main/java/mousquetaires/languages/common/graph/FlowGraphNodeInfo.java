package mousquetaires.languages.common.graph;


import java.util.Objects;


public abstract class FlowGraphNodeInfo {

    protected static final int NON_UNROLLED_DEPTH = 0;

    private final int unrollingDepth;

    public FlowGraphNodeInfo() {
        this(NON_UNROLLED_DEPTH);
    }

    protected FlowGraphNodeInfo(int unrollingDepth) {
        assert unrollingDepth >= 0 : unrollingDepth;
        this.unrollingDepth = unrollingDepth;
    }

    public int getUnrollingDepth() {
        return unrollingDepth;
    }

    public abstract FlowGraphNodeInfo withUnrollingDepth(int newDepth);

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof FlowGraphNodeInfo)) { return false; }
        FlowGraphNodeInfo that = (FlowGraphNodeInfo) o;
        return unrollingDepth == that.unrollingDepth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unrollingDepth);
    }
}
