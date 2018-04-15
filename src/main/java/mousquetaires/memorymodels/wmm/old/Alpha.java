package mousquetaires.memorymodels.wmm.old;

import java.util.Set;
import java.util.stream.Collectors;

import com.microsoft.z3.*;

import dartagnan.program.*;
import mousquetaires.memorymodels.EncodingsOld;


public class Alpha {

    public static BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        Set<Event> eventsL = program.getEvents().stream().filter(e -> e instanceof SharedMemEvent || e instanceof LocalEvent).collect(Collectors.toSet());

        BoolExpr enc = EncodingsOld.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("poloc", "com", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com-alpha", "(co+fr)", "rfe", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satTransFixPoint("idd", eventsL, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("data", "idd^+", "RW", eventsL, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("poloc", "WR", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("data", "(poloc&WR)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satTransFixPoint("(data+(poloc&WR))", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("(data+(poloc&WR))^+", "RM", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("ctrl", "RW", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("(ctrl&RW)", "ctrlisync", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("dp-alpha", "((ctrl&RW)+ctrlisync)", "((data+(poloc&WR))^+&RM)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("dp-alpha", "rf", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("WW", "RM", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("(WW+RM)", "loc", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("po", "((WW+RM)&loc)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("po-alpha", "(po&((WW+RM)&loc))", "mfence", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("ghb-alpha", "po-alpha", "com-alpha", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return ctx.mkAnd(EncodingsOld.satAcyclic("(poloc+com)", events, ctx), EncodingsOld.satAcyclic("(dp-alpha+rf)", events, ctx), EncodingsOld.satAcyclic("ghb-alpha", events, ctx));
    }

    public static BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr enc = ctx.mkAnd(EncodingsOld.satCycleDef("(poloc+com)", events, ctx), EncodingsOld.satCycleDef("(dp-alpha+rf)", events, ctx), EncodingsOld.satCycleDef("ghb-alpha", events, ctx));
        enc = ctx.mkAnd(enc, ctx.mkOr(EncodingsOld.satCycle("(poloc+com)", events, ctx), EncodingsOld.satCycle("(dp-alpha+rf)", events, ctx), EncodingsOld.satCycle("ghb-alpha", events, ctx)));
        return enc;
    }


}