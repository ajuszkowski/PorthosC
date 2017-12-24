package mousquetaires.expression;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.z3.*;

import mousquetaires.program.Location;
import mousquetaires.program.Register;

import static mousquetaires.utils.Utils.lastValueReg;
import static mousquetaires.utils.Utils.lastValueLoc;

public class Assert {

    Map<Register, Integer> regValue = new HashMap<Register, Integer>();
    Map<Location, Integer> locValue = new HashMap<Location, Integer>();

    public Assert() {}

    public void addPair(Register reg, Integer value) {
        regValue.put(reg, value);
    }

    public void addPair(Location loc, Integer value) {
        locValue.put(loc, value);
    }

    public BoolExpr encode(Context ctx) throws Z3Exception {
        BoolExpr enc = ctx.mkTrue();
        for(Register reg: regValue.keySet()) {
            enc = ctx.mkAnd(enc, ctx.mkEq(lastValueReg(reg, ctx), ctx.mkInt(regValue.get(reg))));
        }
        for(Location loc: locValue.keySet()) {
            enc = ctx.mkAnd(enc, ctx.mkEq(lastValueLoc(loc, ctx), ctx.mkInt(locValue.get(loc))));
        }
        return enc;
    }

    public String toString() {
        return regValue.toString() + locValue.toString();
    }
}
