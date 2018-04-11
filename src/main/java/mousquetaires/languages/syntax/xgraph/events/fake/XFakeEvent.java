package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;


public abstract class XFakeEvent extends XEventBase {

    XFakeEvent(int refId, XEventInfo info) {
        super(refId, info);
    }
}
