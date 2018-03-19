package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XJumpEvent extends XFakeEvent {

    public XJumpEvent(XEventInfo info) {
        super(info, NON_REFERENCE_ID);
    }

    private XJumpEvent(XEventInfo info, int fakeEventId, int referenceId) {
        super(info, fakeEventId, referenceId);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getLabel() {
        return "JUMP_" + super.getLabel() + "_" + getFakeEventId();
    }

    @Override
    public XJumpEvent asReference(int referenceId) {
        return new XJumpEvent(getInfo(), getFakeEventId(), referenceId);
    }
}
