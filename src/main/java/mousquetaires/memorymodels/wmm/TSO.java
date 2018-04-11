package mousquetaires.memorymodels.wmm;

import java.util.Set;

import com.microsoft.z3.*;

import dartagnan.program.*;
import mousquetaires.memorymodels.EncodingsOld;


public class TSO {

    public static BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr enc = EncodingsOld.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("poloc", "com", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com-tso", "(co+fr)", "rfe", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satMinus("po", "WR", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("po-tso", "(po\\WR)", "mfence", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("ghb-tso", "po-tso", "com-tso", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return ctx.mkAnd(EncodingsOld.satAcyclic("(poloc+com)", events, ctx), EncodingsOld.satAcyclic("ghb-tso", events, ctx));
    }

    public static BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr enc = ctx.mkAnd(EncodingsOld.satCycleDef("(poloc+com)", events, ctx), EncodingsOld.satCycleDef("ghb-tso", events, ctx));
        enc = ctx.mkAnd(enc, ctx.mkOr(EncodingsOld.satCycle("(poloc+com)", events, ctx), EncodingsOld.satCycle("ghb-tso", events, ctx)));
        return enc;
    }
}