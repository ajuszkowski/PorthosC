package mousquetaires.languages.xrepr.events;

import com.microsoft.z3.Expr;
import mousquetaires.languages.xrepr.memory.XLocation;


public abstract class XMemoryEvent extends XEvent {

    XMemoryEvent(XEventInfo info) {
        super(info);
    }

    public XMemoryEvent(XEventInfo info, XLocation loc) {
        super(info);
        this.loc = loc;
    }

    protected XLocation loc;  // todo: wh'is't?



    // Used by DF_RF to know what SSA number was assigned to the event location
    public Expr ssaLoc;


    public XLocation getLoc() {
        return loc;
    }

}
