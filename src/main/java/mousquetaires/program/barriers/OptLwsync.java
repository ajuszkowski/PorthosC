package mousquetaires.program.barriers;

import java.util.Collections;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;


public class OptLwsync extends Lwsync {

    public String toString() {
        return String.format("%slwsync?", String.join("", Collections.nCopies(condLevel, "  ")));
    }

    public BoolExpr encodeCF(Context ctx) {
        return ctx.mkBoolConst(cfVar());
    }
}
