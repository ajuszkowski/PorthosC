package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public interface XEvent extends XEntity {
    XEventInfo getInfo();
}
