package mousquetaires.utils;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;


public class SmtUtils {
    private final Context ctx;

    public SmtUtils(Context ctx) {
        this.ctx = ctx;
    }

    public BoolExpr and(BoolExpr... vars) {
        return ctx.mkAnd(vars);
    }

    public BoolExpr implies(BoolExpr var1, BoolExpr var2) {
        return ctx.mkImplies(var1, var2);
    }

    public BoolExpr not(BoolExpr var1) {
        return ctx.mkNot(var1);
    }


}
