package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XNopEvent extends XFakeEvent {
    public XNopEvent(XEventInfo info) {
        super(info, NON_REFERENCE_ID);
    }

    XNopEvent(XEventInfo info, int fakeEventId, int referenceId) {
        super(info, fakeEventId, referenceId);
    }

    @Override
    public String getSmtLabel() {
        return "NOP_" + super.getSmtLabel() + "_" + getFakeEventId();
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public XNopEvent asReference(int referenceId) {
        return new XNopEvent(getInfo(), getFakeEventId(), referenceId);
    }
}
