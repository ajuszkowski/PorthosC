package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.common.graph.FlowGraphNodeInfo;

import java.util.Objects;


public class XEventInfo extends FlowGraphNodeInfo {

    private static int stampGlobalCounter = 0;

    // TODO: code origin
    //public final String controlLabel;

    ///** instruction that events come from—which gives the shared variables
    // *  and local registers affected by the event, if any */
    //public final String instruction; // todo: returnType
    /**
     * identifier of the process that event comes from
     */
    private final String processId;

    // todo: perhaps add nullable labels to the event info - for jumps // <- ??
    /**
     * ensures that events are unique
     */
    private final int stamp;


    //todo: package-private (after removing the folder 'tests' from tests project root)
    public XEventInfo(String processId) {
        this(processId, newStamp(), NON_UNROLLED_DEPTH);
    }

    private XEventInfo(String processId, int stamp, int unrollingDepth) {
        super(unrollingDepth);
        // TODO: verify process id string for bad symbols
        this.processId = processId;
        this.stamp = stamp;
    }

    public String getText() {
        return processId + "_" + getStamp();
    }


    public String getProcessId() {
        return processId;
    }

    public int getStamp() {
        return stamp;
    }

    @Override
    public XEventInfo withUnrollingDepth(int newDepth) {
        return new XEventInfo(getProcessId(), getStamp(), newDepth);
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof XEventInfo)) { return false; }
        if (!super.equals(o)) { return false; }
        XEventInfo that = (XEventInfo) o;
        return getStamp() == that.getStamp() &&
                Objects.equals(getProcessId(), that.getProcessId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProcessId(), getStamp());
    }

    public int stamplessHashCode() {
        return Objects.hash(super.hashCode(), getProcessId());
    }

    private static int newStamp() {
        return stampGlobalCounter++;
    }
}
