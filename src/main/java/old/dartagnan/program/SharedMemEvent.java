package old.dartagnan.program;

import com.microsoft.z3.Expr;

public class SharedMemEvent extends Event {

    protected Location loc;
    // Used by DF_RF to know what SSA number was assigned to the event location
    public Expr ssaLoc;

    public SharedMemEvent() {}

    public Location getLoc() {
        return loc;
    }
}