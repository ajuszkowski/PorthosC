package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public abstract class XEventBase implements XEvent {
    private final XEventInfo info;

    public XEventBase(XEventInfo info) {
        this.info = info;
    }

    @Override
    public XEventInfo getInfo() {
        return info;
    }
}
