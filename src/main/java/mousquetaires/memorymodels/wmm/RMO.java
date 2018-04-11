package mousquetaires.memorymodels.wmm;

import java.util.Set;
import java.util.stream.Collectors;

import com.microsoft.z3.*;

import dartagnan.program.*;
import mousquetaires.memorymodels.EncodingsOld;


public class RMO {

    public static BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        Set<Event> eventsL = program.getEvents().stream().filter(e -> e instanceof SharedMemEvent || e instanceof LocalEvent).collect(Collectors.toSet());

        BoolExpr enc = EncodingsOld.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satMinus("poloc", "RR", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("(poloc\\RR)", "com", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com-rmo", "(co+fr)", "rfe", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satTransFixPoint("idd", eventsL, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("data", "idd^+", "RW", eventsL, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("poloc", "WR", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("data", "(poloc&WR)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satTransFixPoint("(data+(poloc&WR))", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("(data+(poloc&WR))^+", "RM", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("ctrl", "RW", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("(ctrl&RW)", "ctrlisync", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("dp-rmo", "((ctrl&RW)+ctrlisync)", "((data+(poloc&WR))^+&RM)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("fence-rmo", "sync", "mfence", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("po-rmo", "dp-rmo", "fence-rmo", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("ghb-rmo", "po-rmo", "com-rmo", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return ctx.mkAnd(EncodingsOld.satAcyclic("((poloc\\RR)+com)", events, ctx), EncodingsOld.satAcyclic("ghb-rmo", events, ctx));
    }

    public static BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr enc = ctx.mkAnd(EncodingsOld.satCycleDef("((poloc\\RR)+com)", events, ctx), EncodingsOld.satCycleDef("ghb-rmo", events, ctx));
        enc = ctx.mkAnd(enc, ctx.mkOr(EncodingsOld.satCycle("((poloc\\RR)+com)", events, ctx), EncodingsOld.satCycle("ghb-rmo", events, ctx)));
        return enc;
    }
}