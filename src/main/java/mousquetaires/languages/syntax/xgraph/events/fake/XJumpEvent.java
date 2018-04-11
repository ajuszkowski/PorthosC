package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XJumpEvent extends XFakeEvent {

    public XJumpEvent(XEventInfo info) {
        this(NOT_UNROLLED_REF_ID, info);
    }

    private XJumpEvent(int refId, XEventInfo info) {
        super(refId, info);
    }

    @Override
    public XJumpEvent asNodeRef(int refId) {
        return new XJumpEvent(refId, getInfo());
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "JUMP_" + super.toString();
    }
}
