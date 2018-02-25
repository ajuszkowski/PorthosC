package mousquetaires.languages.syntax.xgraph.events.auxilaries;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


public class XEntryEvent extends XEventBase {

    public XEntryEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public String toString() {
        return "[ENTRY]";
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        throw new NotImplementedException();
    }

}
