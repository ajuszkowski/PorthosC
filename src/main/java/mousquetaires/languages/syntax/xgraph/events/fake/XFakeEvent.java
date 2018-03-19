package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;


public abstract class XFakeEvent extends XEventBase {

    XFakeEvent(XEventInfo info) {
        super(info, NON_REFERENCE_ID);
    }

    @Override
    public XEvent asReference(int referenceId) {
        throw new NotSupportedException("Fake events cannot have references");
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        throw new NotSupportedException("Fake events should not be visited");
    }
}
