package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public interface XEvent extends XEntity {
    XEventInfo getInfo();

    String getUniqueId();

    //void setNextEvent(XEvent next);
    //
    //XEvent getNextEvent();

    <T extends XEvent> T accept(XEventVisitor<T> visitor);
}
