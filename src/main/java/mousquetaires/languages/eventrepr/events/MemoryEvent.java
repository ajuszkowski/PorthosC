package mousquetaires.languages.eventrepr.events;

import com.microsoft.z3.Expr;
import mousquetaires.languages.eventrepr.memory.MemoryLocation;


public abstract class MemoryEvent extends Event {

    MemoryEvent(EventInfo info) {
        super(info);
    }

    protected MemoryLocation loc;

    // Used by DF_RF to know what SSA number was assigned to the event location
    public Expr ssaLoc;


    public MemoryLocation getLoc() {
        return loc;
    }
}
