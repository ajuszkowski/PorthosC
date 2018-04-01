package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XJumpEvent extends XFakeEvent {

    public XJumpEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public XJumpEvent withInfo(XEventInfo newInfo) {
        return new XJumpEvent(newInfo);
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
