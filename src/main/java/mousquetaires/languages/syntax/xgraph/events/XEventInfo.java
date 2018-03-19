package mousquetaires.languages.syntax.xgraph.events;

import java.util.Objects;


public class XEventInfo {

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
     * ensures that events in a trace are unique
     */
    private final int stamp;

    //todo: package-private (after removing the folder 'tests' from tests project root)
    public XEventInfo(String processId) {
        // TODO: verify process id string for bad symbols
        this.processId = processId;
        this.stamp = newStamp();
    }

    public String getProcessId() {
        return processId;
    }

    public int getStamp() {
        return stamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XEventInfo)) return false;
        XEventInfo that = (XEventInfo) o;
        return Objects.equals(processId, that.processId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processId);
    }

    private static int newStamp() {
        return stampGlobalCounter++;
    }
}
