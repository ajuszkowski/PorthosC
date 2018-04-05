package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.common.graph.FlowGraphNodeInfo;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;

import java.util.Objects;


public class XEventInfo extends FlowGraphNodeInfo {

    private static int eventGlobalCounter = 0;

    // TODO: code origin
    //public final String controlLabel;

    ///** instruction that events come from—which gives the shared variables
    // *  and local registers affected by the event, if any */
    //public final String instruction; // todo: returnType
    /**
     * identifier of the process that event comes from
     */
    private final XProcessId processId;

    // todo: perhaps add nullable labels to the event info - for jumps // <- ??
    /**
     * ensures that events are unique
     */
    private final int eventId;


    //todo: package-private (after removing the folder 'tests' from tests project root)
    public XEventInfo(XProcessId processId) {
        this(processId, newEventId(), NON_UNROLLED_DEPTH);
    }

    private XEventInfo(XProcessId processId, int eventId, int unrollingDepth) {
        super(unrollingDepth);
        // TODO: verify process id string for bad symbols
        this.processId = processId;
        this.eventId = eventId;
    }

    public String getText() {
        return processId + "_" + getEventId();
    }


    public XProcessId getProcessId() {
        return processId;
    }

    public int getEventId() {
        return eventId;
    }

    @Override
    public XEventInfo withUnrollingDepth(int newDepth) {
        return new XEventInfo(getProcessId(), getEventId(), newDepth);
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
        return getEventId() == that.getEventId() &&
                Objects.equals(getProcessId(), that.getProcessId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProcessId(), getEventId());
    }

    public int stamplessHashCode() {
        return Objects.hash(super.hashCode(), getProcessId());
    }

    private static int newEventId() {
        return eventGlobalCounter++;
    }
}
