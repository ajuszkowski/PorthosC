package mousquetaires.memorymodels.wmm;

import com.google.common.collect.ImmutableSet;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
import mousquetaires.memorymodels.Encodings;


public class TSO {

    public static BoolExpr encode(XUnrolledProgram program, Context ctx) {
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        BoolExpr enc = Encodings.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, Encodings.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, Encodings.satUnion("poloc", "com", events, ctx));
        enc = ctx.mkAnd(enc, Encodings.satUnion("com-tso", "(co+fr)", "rfe", events, ctx));
        enc = ctx.mkAnd(enc, Encodings.satMinus("po", "WR", events, ctx));
        enc = ctx.mkAnd(enc, Encodings.satUnion("po-tso", "(po\\WR)", "mfence", events, ctx));
        enc = ctx.mkAnd(enc, Encodings.satUnion("ghb-tso", "po-tso", "com-tso", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(XUnrolledProgram program, Context ctx) {
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        return ctx.mkAnd(Encodings.satAcyclic("(poloc+com)", events, ctx),
                         Encodings.satAcyclic("ghb-tso", events, ctx));
    }

    public static BoolExpr Inconsistent(XUnrolledProgram program, Context ctx) {
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        BoolExpr enc = ctx.mkAnd(Encodings.satCycleDef("(poloc+com)", events, ctx),
                                 Encodings.satCycleDef("ghb-tso", events, ctx));
        enc = ctx.mkAnd(enc, ctx.mkOr(Encodings.satCycle("(poloc+com)", events, ctx),
                                      Encodings.satCycle("ghb-tso", events, ctx)));
        return enc;
    }
}