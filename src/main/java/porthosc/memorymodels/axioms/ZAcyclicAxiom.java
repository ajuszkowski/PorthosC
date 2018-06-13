package porthosc.memorymodels.axioms;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import porthosc.languages.syntax.xgraph.events.XEvent;
import porthosc.memorymodels.Encodings;
import porthosc.memorymodels.relations.ZRelation;

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
