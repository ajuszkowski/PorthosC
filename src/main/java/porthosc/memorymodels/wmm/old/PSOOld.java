package porthosc.memorymodels.wmm.old;

import java.util.Set;

import com.microsoft.z3.*;

import old.dartagnan.program.*;
import porthosc.memorymodels.EncodingsOld;


public class PSOOld {

    public static BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();

        BoolExpr enc = EncodingsOld.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("poloc", "com", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com-pso", "(co+fr)", "rfe", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("po", "RM", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("po-pso", "(po&RM)", "mfence", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("ghb-pso", "po-pso", "com-pso", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return ctx.mkAnd(EncodingsOld.satAcyclic("(poloc+com)", events, ctx), EncodingsOld.satAcyclic("ghb-pso", events, ctx));
    }

    public static BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr enc = ctx.mkAnd(EncodingsOld.satCycleDef("(poloc+com)", events, ctx), EncodingsOld.satCycleDef("ghb-pso", events, ctx));
        enc = ctx.mkAnd(enc, ctx.mkOr(EncodingsOld.satCycle("(poloc+com)", events, ctx), EncodingsOld.satCycle("ghb-pso", events, ctx)));
        return enc;
    }
}