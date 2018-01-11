package mousquetaires.languages.xrepr.events;

import com.microsoft.z3.Expr;
import mousquetaires.languages.xrepr.memory.XMemoryLocation;


public abstract class XMemoryEvent extends XEvent {

    XMemoryEvent(XEventInfo info) {
        super(info);
    }

    protected XMemoryLocation loc;

    // Used by DF_RF to know what SSA number was assigned to the event location
    public Expr ssaLoc;


    public XMemoryLocation getLoc() {
        return loc;
    }
}
