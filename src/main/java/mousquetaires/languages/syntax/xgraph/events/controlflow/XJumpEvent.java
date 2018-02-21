package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public class XJumpEvent extends XEventBase implements XControlFlowEvent {

    public XJumpEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "[JUMP_" + getInfo().getEventId() + "]";
    }

    @Override
    public String getUniqueId() {
        return super.getUniqueId() + "_jump";
    }
}
