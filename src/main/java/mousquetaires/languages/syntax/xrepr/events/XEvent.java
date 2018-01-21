package mousquetaires.languages.syntax.xrepr.events;

import mousquetaires.languages.syntax.xrepr.XEntity;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public interface XEvent extends XEntity {
    XEventInfo getInfo();
}
