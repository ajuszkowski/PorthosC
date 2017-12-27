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
public class RelInterSect extends Relation{
    private Relation r1;
    private Relation r2;

    public RelInterSect(Relation r1, Relation r2, String name) {
        super(name,String.format("(%s&%s)", r1, r2));
        this.r1=r1;
        this.r2=r2;
        containsRec=r1.containsRec || r2.containsRec;
        namedRelations.addAll(r1.namedRelations);
        namedRelations.addAll(r2.namedRelations);
    }
    
    public RelInterSect(Relation r1, Relation r2) {
        super("("+r1.getName()+"&"+r2.getName()+")");
        this.r1=r1;
        this.r2=r2;
        containsRec=r1.containsRec || r2.containsRec;
        namedRelations.addAll(r1.namedRelations);
        namedRelations.addAll(r2.namedRelations);
    }
    
    @Override
    public BoolExpr encode(Program program, Context ctx) {
            BoolExpr enc=r1.encode(program, ctx);
            enc=ctx.mkAnd(enc, r2.encode(program, ctx));
            Set<Event> events = program.getEvents().stream().filter(e -> e instanceof MemEvent).collect(Collectors.toSet());
            for(Event e1 : events) {
            for(Event e2 : events) {
                            BoolExpr opt1=Utils.edge(r1.getName(), e1, e2, ctx);
                            if(r1.containsRec) opt1=ctx.mkAnd(opt1, ctx.mkGt(Utils.intCount(this.getName(),e1,e2, ctx), Utils.intCount(r1.getName(),e1,e2, ctx)));
                            BoolExpr opt2=Utils.edge(r2.getName(), e1, e2, ctx);
                            if(r2.containsRec) opt2=ctx.mkAnd(opt2, ctx.mkGt(Utils.intCount(this.getName(),e1,e2, ctx), Utils.intCount(r2.getName(),e1,e2, ctx)));            
                            enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(name, e1, e2, ctx), ctx.mkAnd(opt1,opt2)));
                                
            }
        }
        return enc;
    }

    
}
