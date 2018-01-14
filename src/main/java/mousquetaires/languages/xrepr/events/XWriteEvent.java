package mousquetaires.languages.xrepr.events;

import mousquetaires.languages.xrepr.memory.XLocalLocation;
import mousquetaires.languages.xrepr.memory.XLocation;
import mousquetaires.languages.xrepr.memory.XSharedLocation;


/** Write event from registry ({@link XLocalLocation})
 * to shared assignee ({@link XSharedLocation})
 */
public class XWriteEvent extends XMemoryEvent {

    public final XLocation source;
    public final XLocation destination;

    public XWriteEvent(XEventInfo info, XLocation source, XLocation destination) {
        super(info);
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "W(" + source +
                ", " + destination +
                ')';
    }
}
