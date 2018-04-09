package mousquetaires.memorymodels.wmm;

import java.util.Set;

import com.microsoft.z3.*;

import dartagnan.program.*;
import mousquetaires.memorymodels.Encodings;


public class SC {

    public static BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr enc = Encodings.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, Encodings.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, Encodings.satUnion("ghb-sc", "po", "com", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return Encodings.satAcyclic("ghb-sc", events, ctx);
    }

    public static BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return ctx.mkAnd(Encodings.satCycleDef("ghb-sc", events, ctx), Encodings.satCycle("ghb-sc", events, ctx));
    }
}