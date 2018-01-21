package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.syntax.xrepr.events.XEventBase;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;


public abstract class XMemoryEvent extends XEventBase {

    private final XMemoryUnit destination;
    private final XMemoryUnit source;

    XMemoryEvent(XEventInfo info, XMemoryUnit destination, XMemoryUnit source) {
        super(info);
        this.destination = destination;
        this.source = source;
    }

    public XMemoryUnit getDestination() {
        return destination;
    }

    public XMemoryUnit getSource() {
        return source;
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
