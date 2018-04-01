/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dartagnan.wmm;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Program;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Florian Furbach
 */
public class BasicRelation extends Relation{
        public static final String BASERELS[] = {"po", "co", "fr", "rf", "poloc", "rfe", "WR", "mfence"};

    public BasicRelation(String rel) {
        super(rel);
        containsRec=false;
    }

    @Override
    public BoolExpr encodeBasic(Program program, Context ctx) throws Z3Exception {
        return ctx.mkTrue();
    }

    @Override
    public BoolExpr encode(Program program, Context ctx, Set<String> encodedRels) throws Z3Exception {
        return ctx.mkTrue();
    }
    
    
    
}
