package mousquetaires.languages.xrepr.events.memory;

import mousquetaires.languages.xrepr.events.XEvent;
import mousquetaires.languages.xrepr.events.XEventInfo;


public abstract class XMemoryEvent extends XEvent {

    XMemoryEvent(XEventInfo info) {
        super(info);
    }

    //public XMemoryEvent(XEventInfo info, XMemoryUnit loc) {
    //    super(info);
    //    this.loc = loc;
    //}
    //
    //protected XMemoryUnit loc;  // todo: wh'is't?
    //
    //
    //
    //// Used by DF_RF to know what SSA number was assigned to the event location
    //public Expr ssaLoc;
    //
    //
    //public XMemoryUnit getLoc() {
    //    return loc;
    //}

}
