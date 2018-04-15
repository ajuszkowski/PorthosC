package mousquetaires.memorymodels.axioms;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.memorymodels.relations.ZRelation;

import java.util.Set;


public abstract class ZAxiom {

    protected ZRelation rel;

    public ZAxiom(ZRelation rel) {
        this.rel = rel;
    }

    public ZRelation getRel() {
        return rel;
    }

    public abstract String write();

    public abstract BoolExpr Consistent(Set<? extends XEvent> events, Context ctx);

    public abstract BoolExpr Inconsistent(Set<? extends XEvent> events, Context ctx);

}
