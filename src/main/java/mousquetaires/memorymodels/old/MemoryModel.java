/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousquetaires.memorymodels.old;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.program.events.old.Event;
import mousquetaires.program.events.old.MemEvent;
import mousquetaires.program.Program;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 *
 * @author Florian Furbach
 */
public class MemoryModel {

    private Vector<Axiom> axioms=new Vector<>();
    private Vector<Relation> namedrels=new Vector<>();
    
    public void addAxiom(Axiom ax){
        axioms.add(ax);
    }
    public void addRel(Relation rel){
        namedrels.add(rel);
    }   
    public BoolExpr encode(Program program, Context ctx) {
        BoolExpr expr = ctx.mkTrue();
        for (Axiom ax : axioms) {
            expr=ctx.mkAnd(expr,ax.getRel().encode(program, ctx));
        }
        return expr;
    }
    
    public BoolExpr Consistent(Program program, Context ctx) {
        Set<Event> events = program.getEvents().stream().filter(e -> e instanceof MemEvent).collect(Collectors.toSet());
        BoolExpr expr = ctx.mkTrue();
        for (Relation namedrel : namedrels) {
            expr=ctx.mkAnd(expr,namedrel.encode(program, ctx));
        }
        for (Axiom ax : axioms) {
            expr=ctx.mkAnd(expr,ax.Consistent(events, ctx));
        }
        return expr;
    }
    
        public BoolExpr Inconsistent(Program program, Context ctx) {
        Set<Event> events = program.getEvents().stream().filter(e -> e instanceof MemEvent).collect(Collectors.toSet());
        BoolExpr expr = ctx.mkFalse();
        for (Axiom ax : axioms) {
            expr=ctx.mkOr(expr,ax.Inconsistent(events, ctx));
        }
        return expr;
    }
        
    public String write(){
        String temp="";
        Set<Relation> named=new HashSet<>();
        for (Axiom axiom : axioms) {
            temp=temp+axiom.write()+"\n";
            named.addAll(axiom.getRel().getNamedRelations());
        }
        for (Relation relation : named) {
            temp=temp+relation.write()+"\n";
        }
        return temp;
    }
    
}
