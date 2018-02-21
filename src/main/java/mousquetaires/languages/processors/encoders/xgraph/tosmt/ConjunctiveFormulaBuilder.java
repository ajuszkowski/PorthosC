package mousquetaires.languages.processors.encoders.xgraph.tosmt;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.utils.patterns.Builder;

import java.util.LinkedList;
import java.util.List;


public class ConjunctiveFormulaBuilder extends Builder<BoolExpr> {
    private final Context ctx;
    private final List<BoolExpr> conjuncts;

    public ConjunctiveFormulaBuilder(Context ctx) {
        this.ctx = ctx;
        this.conjuncts = new LinkedList<>();
    }


    public void addConjunct(BoolExpr conjunct) {
        add(conjunct, conjuncts);
    }

    @Override

    public BoolExpr build() {
        markFinished();
        assert conjuncts.size() > 0 : "Attempt to build an empty CNF formula";
        return ctx.mkAnd(conjuncts.toArray(new BoolExpr[0]));
    }
}
