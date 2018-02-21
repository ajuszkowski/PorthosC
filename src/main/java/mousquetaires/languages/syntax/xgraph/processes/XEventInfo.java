package mousquetaires.languages.syntax.xgraph.processes;

public class XEventInfo {

    /** identifier of the process that event comes from */
    private final String processId;

    // TODO: code origin
    //public final String controlLabel;

    ///** instruction that events come from—which gives the shared variables
    // *  and local registers affected by the event, if any */
    //public final String instruction; // todo: returnType

    /** ensures that events in a trace are unique */
    private final int stamp;
    // todo: perhaps add nullable labels to the event info - for jumps // <- ??

    XEventInfo(String processId) {
        this.processId = processId;
        this.stamp = newStamp();
    }

    // e.g.: p1_e32
    public String getEventId() {
        return processId + "_e" + stamp;
    }


    private static int stampGlobalCounter = 0;
    private static int newStamp() {
        return stampGlobalCounter++;
    }

    //todo: hashcode
}
