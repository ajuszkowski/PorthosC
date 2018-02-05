package mousquetaires.languages.syntax.xgraph.events.fakes;

import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public class XExitEvent extends XFakeEvent {

    public XExitEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public String toString() {
        return "[EXIT]";
    }
}
