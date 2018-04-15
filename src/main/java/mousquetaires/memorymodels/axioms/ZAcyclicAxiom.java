package mousquetaires.memorymodels.axioms;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.memorymodels.Encodings;
import mousquetaires.memorymodels.relations.ZRelation;

import java.util.Set;


public class ZAcyclicAxiom extends ZAxiom {

    public ZAcyclicAxiom(ZRelation rel) {
        super(rel);
    }

    @Override
    public BoolExpr Consistent(Set<? extends XEvent> events, Context ctx) {
        return Encodings.satAcyclic(rel.getName(), events, ctx);
    }

    @Override
    public BoolExpr Inconsistent(Set<? extends XEvent> events, Context ctx) {
        return ctx.mkAnd(Encodings.satCycleDef(rel.getName(), events, ctx),
                         Encodings.satCycle(rel.getName(), events, ctx));
    }

    @Override
    public String write() {
        return String.format("Acyclic(%s)", rel.getName());
    }

}
