package mousquetaires.memorymodels.wmm;

import com.google.common.collect.ImmutableSet;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
import mousquetaires.memorymodels.Encodings;


public class SC {

    public static BoolExpr encode(XUnrolledProgram program, Context ctx) {
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        BoolExpr enc = Encodings.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, Encodings.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, Encodings.satUnion("ghb-sc", "po", "com", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(XUnrolledProgram program, Context ctx) {
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        return Encodings.satAcyclic("ghb-sc", events, ctx);
    }

    public static BoolExpr Inconsistent(XUnrolledProgram program, Context ctx) {
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        return ctx.mkAnd(Encodings.satCycleDef("ghb-sc", events, ctx), Encodings.satCycle("ghb-sc", events, ctx));
    }
}