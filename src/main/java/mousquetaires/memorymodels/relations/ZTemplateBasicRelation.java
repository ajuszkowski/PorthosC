package mousquetaires.memorymodels.relations;

import aramis.ListOfRels;
import com.google.common.collect.ImmutableSet;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Z3Exception;
import mousquetaires.languages.syntax.xgraph.program.XProgram;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
import mousquetaires.utils.Utils;

import java.util.Set;


public class ZTemplateBasicRelation extends ZRelation {

    public ZTemplateBasicRelation() {
        super("TBR" + String.valueOf(ZTemplateRelation.ID));
        ZTemplateRelation.ID++;
    }

    /**
     * @param s
     * @param ctx
     * @return
     */
    public ZBasicRelation getSolution(Solver s, Context ctx) {
        for (String baserel : ListOfRels.baserels) {
            BoolExpr baserelid = ctx.mkBoolConst(baserel + name);
            if (s.getModel().eval(baserelid, true).isTrue()) { return new ZBasicRelation(baserel); }
        }
        System.err.println("could not find solution for basic relation template " + getName());
        return null;
    }

    @Override
    public BoolExpr encode(XProgram program, Context ctx, Set<String> encodedRels) throws Z3Exception {
        return encodeBasic(program, ctx);
    }

    @Override
    protected BoolExpr encodeBasic(XProgram program, Context ctx) throws Z3Exception {
        BoolExpr enc = ctx.mkFalse();
        //Set<XEvent> events = program.getMemEvents();
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        for (String baserel : ListOfRels.baserels) {
            BoolExpr enc2 = ctx.mkTrue();
            BoolExpr baserelid = ctx.mkBoolConst(baserel + name);
            for (XEvent e1 : events) {
                for (XEvent e2 : events) {
                    enc2 = ctx.mkAnd(enc2, ctx.mkEq(Utils.edge(getName(), e1, e2, ctx),
                                                    Utils.edge(ZTemplateRelation.PREFIX + baserel, e1, e2, ctx)));
                }
            }
            enc = ctx.mkOr(ctx.mkAnd(ctx.mkNot(baserelid), enc), ctx.mkAnd(baserelid, enc2));

        }
        return enc;
    }

}
