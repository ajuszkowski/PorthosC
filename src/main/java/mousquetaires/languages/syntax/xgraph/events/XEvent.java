package mousquetaires.languages.syntax.xgraph.events;


import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public interface XEvent extends FlowGraphNode<XEventInfo> {

    <T> T accept(XEventVisitor<T> visitor);

    @Override
    XEvent withInfo(XEventInfo newInfo);
}
