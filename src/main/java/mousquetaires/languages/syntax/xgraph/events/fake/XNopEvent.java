package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XNopEvent extends XFakeEvent {
    public XNopEvent(XEventInfo info) {
        super(info, NON_REFERENCE_ID);
    }

    XNopEvent(XEventInfo info, int referenceId) {
        super(info, referenceId);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NOP_" + super.toString();
    }

    @Override
    public XNopEvent asReference(int referenceId) {
        return new XNopEvent(getInfo(), referenceId);
    }
}
