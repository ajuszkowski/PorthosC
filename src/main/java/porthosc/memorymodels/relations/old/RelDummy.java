package porthosc.memorymodels.relations.old;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import old.dartagnan.program.Program;

import java.util.Set;

public class RelDummy extends Relation{

    private Relation dummyOf;

    public Relation getDummyOf() {
        return dummyOf;
    }

    public void setDummyOf(Relation dummyOf) {
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
