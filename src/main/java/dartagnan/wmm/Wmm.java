/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dartagnan.wmm;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Event;
import dartagnan.program.MemEvent;
import dartagnan.program.Program;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 *
 * @author Florian Furbach
 */
public class Wmm {
    
    public static Wmm getWmm(String model){
        Wmm temp=new Wmm();
        Relation co=new BasicRelation("co");
        Relation po=new BasicRelation("po");
        Relation fr=new BasicRelation("fr");
        Relation rf=new BasicRelation("rf");
        Relation com=new RelUnion(new RelUnion(co, fr), rf, "com");
        Relation ghbsc=new RelUnion(po, com, "ghb-sc");
        
        if(model.contentEquals("sc")){
            temp.addAxiom(new Acyclic(ghbsc));
            return temp;
        }  
        
        Relation poloc=new BasicRelation("poloc");
        Relation rfe=new BasicRelation("rfe");
        Relation comtso=new RelUnion(new RelUnion(co,fr), rfe, "com-tso");
        Relation WR=new BasicRelation("WR");
        Relation mfence=new BasicRelation("mfence");
        Relation potso=new RelUnion(new RelMinus(po, WR), mfence, "po-tso");
        Relation ghbtso=new RelUnion(potso, comtso, "ghb-tso");
    
        if(model.contentEquals("tso")) {
            temp.addAxiom(new Acyclic(ghbtso));
            temp.addAxiom(new Acyclic(new RelUnion(poloc, com)));
            return temp;
        };        
            
        return null;
    }
       
    
    private Vector<Axiom> axioms=new Vector<>();
    private Vector<Relation> namedrels=new Vector<>();
    
    public void addAxiom(Axiom ax){
        axioms.add(ax);
    }
    public void addRel(Relation rel){
        namedrels.add(rel);
    }   
    public BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        BoolExpr expr = ctx.mkTrue();
        for (Axiom ax : axioms) {
            expr=ctx.mkAnd(expr,ax.getRel().encode(program, ctx));
        }
        return expr;
    }
    
    public BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
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
    
        public BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
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
