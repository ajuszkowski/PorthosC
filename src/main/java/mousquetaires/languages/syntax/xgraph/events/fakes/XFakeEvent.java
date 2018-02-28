package mousquetaires.languages.syntax.xgraph.events.fakes;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;


public abstract class XFakeEvent extends XEventBase {

    public XFakeEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        throw new NotSupportedException();
    }

    @Override
    public String toString() {
        return "FAKE_EVENT"; //TODO: there must be no fake events in the result graph
    }
}
