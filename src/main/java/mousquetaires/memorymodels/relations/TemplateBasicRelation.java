/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousquetaires.memorymodels.relations;

import aramis.ListOfRels;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Event;
import dartagnan.program.Program;
import mousquetaires.utils.Utils;
import java.util.Set;


/**
 *
 * @author Florian Furbach
 */
public class TemplateBasicRelation extends Relation {

    /**
     *
     * @param s
     * @param ctx
     * @return
     */
    public BasicRelation getSolution(Solver s,Context ctx){
            for (String baserel : ListOfRels.baserels) {
                BoolExpr baserelid = ctx.mkBoolConst(baserel + name);
                if(s.getModel().eval(baserelid, true).isTrue()) return new BasicRelation(baserel);
            }
            System.err.println("could not find solution for basic relation template "+getName());
            return null;
        }

    
    public TemplateBasicRelation() {
        super("TBR"+String.valueOf(TemplateRelation.ID));
        TemplateRelation.ID++;
    }

    @Override
    public BoolExpr encode(Program program, Context ctx, Set<String> encodedRels) throws Z3Exception {
        return encodeBasic(program, ctx);
    }

    @Override
    protected BoolExpr encodeBasic(Program program, Context ctx) throws Z3Exception {
        BoolExpr enc = ctx.mkFalse();
        Set<Event> events = program.getMemEvents();
        for (String baserel : ListOfRels.baserels) {
            BoolExpr enc2 = ctx.mkTrue();
            BoolExpr baserelid = ctx.mkBoolConst(baserel + name);
            for (Event e1 : events) {
                for (Event e2 : events) {
                    enc2 = ctx.mkAnd(enc2, ctx.mkEq(Utils.edge(getName(), e1, e2, ctx), Utils.edge(TemplateRelation.PREFIX+baserel, e1, e2, ctx)));
                }
            }
            enc = ctx.mkOr(ctx.mkAnd(ctx.mkNot(baserelid), enc), ctx.mkAnd(baserelid, enc2));

        }
        return enc;
    }

}
