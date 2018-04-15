package mousquetaires.memorymodels.relations;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;

import java.util.Set;


public class ZRelDummy extends ZRelation {

    private ZRelation dummyOf;

    public ZRelDummy(String name) {
        super(name);
        containsRec = true;
    }

    public ZRelation getDummyOf() {
        return dummyOf;
    }

    public void setDummyOf(ZRelation dummyOf) {
        this.dummyOf = dummyOf;
    }

    @Override
    public BoolExpr encode(XUnrolledProgram program, Context ctx, Set<String> encodedRels) throws Z3Exception {
        return ctx.mkTrue();
    }

    @Override
    protected BoolExpr encodeBasic(XUnrolledProgram program, Context ctx) throws Z3Exception {
        return ctx.mkTrue();
    }
}
