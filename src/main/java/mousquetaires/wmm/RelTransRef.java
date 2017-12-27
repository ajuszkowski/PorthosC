/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousquetaires.wmm;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import mousquetaires.program.Event;
import mousquetaires.program.MemEvent;
import mousquetaires.program.Program;
import mousquetaires.utils.Utils;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Florian Furbach
 */
public class RelTransRef extends Relation {

    private Relation r1;

    public RelTransRef(Relation r1, String name) {
        super(name,String.format("(%s)*", r1.getName()));
        this.r1 = r1;
        containsRec = r1.containsRec;
        namedRelations.addAll(r1.getNamedRelations());

    }

    public RelTransRef(Relation r1) {
        super(String.format("(%s)*", r1.getName()));
        this.r1 = r1;
        containsRec = r1.containsRec;
        namedRelations.addAll(r1.getNamedRelations());
    }

    @Override
    public BoolExpr encode(Program program, Context ctx) {
        BoolExpr enc = r1.encode(program, ctx);
        Set<Event> events = program.getEvents().stream().filter(e -> e instanceof MemEvent).collect(Collectors.toSet());
        //copied from satTansIDL
        for (Event e1 : events) {
            for (Event e2 : events) {
                //reflexive
                if (e1 == e2) {
                    enc = ctx.mkAnd(enc, Utils.edge(this.getName(), e1, e2, ctx));
                } else {
                    //transitive
                    BoolExpr orClause = ctx.mkFalse();
                    for (Event e3 : events) {
                        orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge(this.getName(), e1, e3, ctx), Utils.edge(this.getName(), e3, e2, ctx),
                                ctx.mkGt(Utils.intCount(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx), Utils.intCount(this.getName(), e1, e3, ctx)),
                                ctx.mkGt(Utils.intCount(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx), Utils.intCount(this.getName(), e3, e2, ctx))));
                    }
                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx), orClause));
                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(this.getName(), e1, e2, ctx), ctx.mkOr(
                            ctx.mkAnd(Utils.edge(r1.getName(), e1, e2, ctx), ctx.mkGt(Utils.intCount(this.getName(), e1, e2, ctx), Utils.intCount(r1.getName(), e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx), ctx.mkGt(Utils.intCount(this.getName(), e1, e2, ctx), Utils.intCount(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx))))));
                }

            }
        }
        return enc;
    }

}
