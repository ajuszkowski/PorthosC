package mousquetaires.memorymodels.relations;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Program;

import java.util.Set;


public class RelDummy extends ZRelation {

    private ZRelation dummyOf;

    public ZRelation getDummyOf() {
        return dummyOf;
    }

    public void setDummyOf(ZRelation dummyOf) {
        this.dummyOf = dummyOf;
    }
    
    public RelDummy(String name) {
        super(name);
        containsRec=true;
    }

    @Override
    public BoolExpr encode(Program program, Context ctx, Set<String> encodedRels) throws Z3Exception {
        return ctx.mkTrue();
    }

    @Override
    protected BoolExpr encodeBasic(Program program, Context ctx) throws Z3Exception {
        return ctx.mkTrue();
     }
    
}
