package mousquetaires.memorymodels.wmm.old;

import java.util.Set;

import com.microsoft.z3.*;

import dartagnan.program.*;
import mousquetaires.memorymodels.EncodingsOld;


public class SC {

    public static BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr enc = EncodingsOld.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("ghb-sc", "po", "com", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return EncodingsOld.satAcyclic("ghb-sc", events, ctx);
    }

    public static BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return ctx.mkAnd(EncodingsOld.satCycleDef("ghb-sc", events, ctx), EncodingsOld.satCycle("ghb-sc", events, ctx));
    }
}