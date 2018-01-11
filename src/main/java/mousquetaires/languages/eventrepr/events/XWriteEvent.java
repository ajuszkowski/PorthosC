package mousquetaires.languages.eventrepr.events;

import mousquetaires.languages.eventrepr.memory.XSharedLocation;
import mousquetaires.languages.eventrepr.memory.XRegistryLocation;


/** Write event from registry ({@link XRegistryLocation})
 * to shared assignee ({@link XSharedLocation})
 */
public class XWriteEvent extends XMemoryEvent {

    public final XRegistryLocation source;
    public final XSharedLocation destination;

    public XWriteEvent(XEventInfo info, XRegistryLocation source, XSharedLocation destination) {
        super(info);
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "w(" + source +
                ", " + destination +
                ')';
    }
}
