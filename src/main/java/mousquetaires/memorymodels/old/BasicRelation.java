/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousquetaires.memorymodels.old;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.program.Program;


/**
 *
 * @author Florian Furbach
 */
public class BasicRelation extends Relation{

    public BasicRelation(String rel) {
        super(rel);
        containsRec=false;
    }

    @Override
    public BoolExpr encode(Program program, Context ctx) {
        return ctx.mkTrue();
    }
    
    
    
}
