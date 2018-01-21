package mousquetaires.languages.syntax.xrepr.events;

import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public abstract class XEventBase implements XEvent {
    public final XEventInfo info;

    public XEventBase(XEventInfo info) {
        this.info = info;
    }

    @Override
    public XEventInfo getInfo() {
        return info;
    }

    // todo: perhaps add nullable labels to the event info - for jumps
}
