package mousquetaires.languages.syntax.xgraph.events.auxilaries;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


public class XExitEvent extends XEventBase {

    public XExitEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public String toString() {
        return "[EXIT]";
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
        return other instanceof XExitEvent &&
                getInfo().getProcessId().equals(other.getInfo().getProcessId());
    }

    //todo: override hashcode!

}
