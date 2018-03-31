package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XNopEvent extends XFakeEvent {
    public XNopEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public XNopEvent withInfo(XEventInfo newInfo) {
        return new XNopEvent(newInfo);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NOP_" + super.toString();
    }
}
