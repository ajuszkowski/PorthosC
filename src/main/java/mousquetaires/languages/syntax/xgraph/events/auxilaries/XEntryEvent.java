package mousquetaires.languages.syntax.xgraph.events.auxilaries;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XEvent)) return false;
        XEvent other = (XEvent) o;
        return other instanceof XEntryEvent &&
                getInfo().getProcessId().equals(other.getInfo().getProcessId());
    }

    //todo: override hashcode!
}
