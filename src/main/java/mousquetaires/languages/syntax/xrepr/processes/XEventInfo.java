package mousquetaires.languages.syntax.xrepr.processes;

import java.util.Objects;


public class XEventInfo {

    /** identifier of the process that event comes from */
    private final int processId;

    // TODO: code origin
    //public final String controlLabel;

    ///** instruction that events come from—which gives the shared variables
    // *  and local registers affected by the event, if any */
    //public final String instruction; // todo: returnType

    /** ensures that events in a trace are unique */
    private final int stamp;
    // todo: perhaps add nullable labels to the event info - for jumps // <- ??

    XEventInfo(int processId) {
        this.processId = processId;
        this.stamp = newStamp();
    }

    public int getProcessId() {
        return processId;
    }

    public int getStamp() {
        return stamp;
    }

    public static int getStampGlobalCounter() {
        return stampGlobalCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XEventInfo)) return false;
        XEventInfo that = (XEventInfo) o;
        return getProcessId() == that.getProcessId() &&
                getStamp() == that.getStamp();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProcessId(), getStamp());
    }

    private static int stampGlobalCounter = 0;
    private static int newStamp() {
        return stampGlobalCounter++;
    }
}
