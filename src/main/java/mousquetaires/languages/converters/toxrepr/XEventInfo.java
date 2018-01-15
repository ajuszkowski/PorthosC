package mousquetaires.languages.converters.toxrepr;

import java.util.Objects;


public class XEventInfo {

    /** identifier of the process that event comes from */
    public final String processName;

    // TODO: code origin
    //public final String controlLabel;

    ///** instruction that events come from—which gives the shared variables
    // *  and local registers affected by the event, if any */
    //public final String instruction; // todo: type

    /** ensures that events in a trace are unique */
    public final int stamp;

    XEventInfo(String name) {
        this.processName = name;
        this.stamp = newStamp();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XEventInfo)) return false;
        XEventInfo that = (XEventInfo) o;
        return stamp == that.stamp &&
                Objects.equals(processName, that.processName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(processName, stamp);
    }

    private static int stampGlobalCounter = 0;
    private static int newStamp() {
        return stampGlobalCounter++;
    }
}
