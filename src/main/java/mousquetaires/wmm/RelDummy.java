/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousquetaires.wmm;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import mousquetaires.program.Program;

/**
 *
 * @author Florian Furbach
 */
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
    public BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        return ctx.mkTrue();
    }
    
}
