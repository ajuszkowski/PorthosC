package mousquetaires.languages.xrepr.events;

import mousquetaires.languages.xrepr.memory.XSharedLocation;

/** Read event from shared assignee ({@link XSharedLocation}) */
public class XReadEvent extends XMemoryEvent {

    public final XSharedLocation source;

    // todo: value - ?

    public XReadEvent(XEventInfo info, XSharedLocation source) {
        super(info);
        this.source = source;
    }

    @Override
    public String toString() {
        return "R(" + source + ')';
    }
}
