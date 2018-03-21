package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;

public interface XEvent extends XEntity, GraphNode {

    XEventInfo getInfo();

    //String getLabel();

    XEvent asReference(int referenceId);

    <T> T accept(XEventVisitor<T> visitor);
}
