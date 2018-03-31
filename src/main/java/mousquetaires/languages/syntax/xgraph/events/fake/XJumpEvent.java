package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XJumpEvent extends XFakeEvent {

    public XJumpEvent(XEventInfo info) {
        super(info, NON_REFERENCE_ID);
    }

    private XJumpEvent(XEventInfo info, int referenceId) {
        super(info, referenceId);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "JUMP_" + super.toString();
    }

    @Override
    public XJumpEvent asReference(int referenceId) {
        return new XJumpEvent(getInfo(), referenceId);
    }
}
