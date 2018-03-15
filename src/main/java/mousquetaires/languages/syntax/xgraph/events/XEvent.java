package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.common.graph.Node;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public interface XEvent extends XEntity, Node {

    XEventInfo getInfo();

    String getUniqueId();

    //void setNextEvent(XEvent next);
    //
    //XEvent getNextEvent();

    <T> T accept(XEventVisitor<T> visitor);
}
