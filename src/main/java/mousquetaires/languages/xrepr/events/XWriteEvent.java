package mousquetaires.languages.xrepr.events;

import mousquetaires.languages.xrepr.memory.XSharedLocation;
import mousquetaires.languages.xrepr.memory.XLocalLocation;


/** Write event from registry ({@link XLocalLocation})
 * to shared assignee ({@link XSharedLocation})
 */
public class XWriteEvent extends XMemoryEvent {

    public final XLocalLocation source;
    public final XSharedLocation destination;

    public XWriteEvent(XEventInfo info, XLocalLocation source, XSharedLocation destination) {
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
