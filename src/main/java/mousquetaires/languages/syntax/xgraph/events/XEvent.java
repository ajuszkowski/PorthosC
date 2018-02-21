package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public interface XEvent extends XEntity {
    XEventInfo getInfo();

    String getUniqueId();

    //void setNextEvent(XEvent next);
    //
    //XEvent getNextEvent();

    <T> T accept(XgraphVisitor<T> visitor);
}
