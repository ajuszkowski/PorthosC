package mousquetaires.languages.syntax.xgraph.events.fakes;

import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public class XEntryEvent extends XFakeEvent {

    public XEntryEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public String toString() {
        return "[ENTRY]";
    }
}
