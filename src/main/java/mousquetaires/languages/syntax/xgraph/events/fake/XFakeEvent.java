package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;

import java.util.Objects;


public abstract class XFakeEvent extends XEventBase {
    XFakeEvent(XEventInfo info, int referenceId) {
        super(info, referenceId);
    }

}
