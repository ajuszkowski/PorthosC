package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;

public interface XEvent extends XEntity, FlowGraphNode {

    XEventInfo getInfo();

    @Override
    default String nodeId() {
        return uniqueId();
    }

    XEvent asReference(int referenceId);

    <T> T accept(XEventVisitor<T> visitor);
}
