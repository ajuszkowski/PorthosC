package mousquetaires.memorymodels.axioms;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Event;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.memorymodels.Encodings;
import mousquetaires.memorymodels.relations.ZRelation;
import mousquetaires.memorymodels.relations.old.Relation;
import mousquetaires.utils.Utils;

import java.util.Set;


public class ZIrreflexiveAxiom extends ZAxiom {

    public ZIrreflexiveAxiom(ZRelation rel) {
        super(rel);
    }

    @Override
    public BoolExpr Consistent(Set<? extends XEvent> events, Context ctx) {
        return Encodings.satIrref(rel.getName(), events, ctx);
    }

    @Override
    public BoolExpr Inconsistent(Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e : events) {
            enc = ctx.mkOr(enc, Utils.edge(rel.getName(), e, e, ctx));
        }
        return enc;
    }

    @Override
    public String write() {
        return String.format("Irreflexive(%s)", rel.getName());
    }

}
