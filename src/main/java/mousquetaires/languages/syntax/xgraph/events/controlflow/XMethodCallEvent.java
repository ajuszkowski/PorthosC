package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public class XMethodCallEvent extends XEventBase implements XControlFlowEvent {

    // todo
    public XMethodCallEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
