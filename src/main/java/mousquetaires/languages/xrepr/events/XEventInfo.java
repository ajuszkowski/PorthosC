package mousquetaires.languages.xrepr.events;

import java.util.Objects;


public class XEventInfo {
    /** identifier of the process that event comes from */
    public final short processIdentifier;

    /** control label of the instruction that event comes from */
    public final String controlLabel;

    /** instruction that events come from—which gives the shared variables
     *  and local registers affected by the event, if any */
    public final String instruction; // todo: type

    /** ensures that events in a trace are unique */
    public final int stamp;

    public XEventInfo(short processIdentifier, String controlLabel, String instruction) {
        this.processIdentifier = processIdentifier;
        this.controlLabel = controlLabel;
        this.instruction = instruction;
        this.stamp = newStamp();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof XEventInfo)) {
            return false;
        }
        XEventInfo eventInfo = (XEventInfo) o;
        return processIdentifier == eventInfo.processIdentifier &&
                stamp == eventInfo.stamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(processIdentifier, stamp);
    }

    private static int stampGlobalCounter = 0;
    private static int newStamp() {
        return stampGlobalCounter++;
    }
}
