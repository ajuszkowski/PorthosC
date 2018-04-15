/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousquetaires.memorymodels.wmm.old;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Event;
import dartagnan.program.Program;
import mousquetaires.memorymodels.axioms.old.Acyclic;
import mousquetaires.memorymodels.axioms.old.Axiom;
import mousquetaires.memorymodels.axioms.old.Irreflexive;
import mousquetaires.memorymodels.relations.old.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;


/**
 *
 * @author Florian Furbach
 */
public class Wmm {

    public static Wmm getWmm(String model) {
        Wmm temp = new Wmm();
        //basics:
        Relation co = new BasicRelation("co");
        Relation po = new BasicRelation("po");
        Relation fr = new BasicRelation("fr");
        Relation rf = new BasicRelation("rf");
        Relation com = new RelUnion(new RelUnion(co, fr), rf, "com");

        //sc:
        if (model.contentEquals("sc")) {
            Relation ghbsc = new RelUnion(po, com, "ghb-sc");
            temp.addAxiom(new Acyclic(ghbsc));
            return temp;
        }

        //tso:
        Relation poloc = new BasicRelation("poloc");
        Relation rfe = new BasicRelation("rfe");
        Relation WR = new BasicRelation("WR");
        if (model.contentEquals("tso")) {
            Relation comtso = new RelUnion(new RelUnion(co, fr), rfe, "com-tso");
            Relation mfence = new BasicRelation("mfence");
            Relation potso = new RelUnion(new RelMinus(po, WR), mfence, "po-tso");
            Relation ghbtso = new RelUnion(potso, comtso, "ghb-tso");
            temp.addAxiom(new Acyclic(ghbtso));
            temp.addAxiom(new Acyclic(new RelUnion(poloc, com)));
            return temp;
        }

        if (model.contentEquals("power")) {
            //acyclic((po ∩ sloc) ∪ rf ∪ fr ∪ co)
            //Relation sloc=new BasicRelation("sloc");
            temp.addAxiom(new Acyclic(new RelUnion(poloc, new RelUnion(new RelUnion(rf, fr), co))));
            //ppo:
            //dp := ad ∪ dd           
            //TODO: why is addr (ad) empty?
            Relation RW=new BasicRelation("RW");
            Relation dd = new RelInterSect(new RelLocTrans(new BasicRelation("idd")), RW);
            Relation dp = dd;
            //rdw := (po ∩ sloc) ∩ (fre;rfe):
            Relation fre=new BasicRelation("fre");
            Relation rdw = new RelInterSect(poloc, new RelComposition(fre, rfe));
            // detour := (po ∩ sloc) ∩ (coe;rfe)
            Relation detour = new RelInterSect(poloc, new RelComposition(new BasicRelation("coe"), rfe));
            //ii0 := dp ∪ rdw ∪ rfi 
            Relation ii0=new RelUnion(dp, new RelUnion(rdw, new BasicRelation("rfi")));
            //ci0 := cd-isync ∪ detour
            Relation ci0=new RelUnion(new BasicRelation("ctrlisync"), detour);
            //cc0 :=dp∪(po∩sloc)∪cd∪(ad;po) addr is empty ?
            Relation cc0=new RelUnion(dp, new RelUnion(poloc, new BasicRelation("ctrl")));
            //Relation cc0=new RelUnion(dp, new RelUnion(poloc, new RelUnion(new BasicRelation("ctrl"), new RelComposition(ad, po))));
            //ii:=ii0 ∪ci∪(ic;ci)∪(ii;ii) 
            Relation iidummy=new RelDummy("ii");
            Relation icdummy=new RelDummy("ic");
            Relation cidummy=new RelDummy("ci");                    
            Relation ccdummy=new RelDummy("cc");                    
            Relation ii=new RelUnion(ii0, new RelUnion(cidummy, new RelUnion(new RelComposition(icdummy, cidummy), new RelComposition(iidummy, iidummy))),"ii");
            //ci:=ci0 ∪(ci;ii)∪(cc;ci)
            Relation ci=new RelUnion(ci0, new RelUnion(new RelComposition(cidummy, ii), new RelComposition(ccdummy, cidummy)),"ci");
            //ic := ic0 ∪ii ∪cc ∪(ic;cc)∪(ii;ic) 
            Relation ic=new RelUnion(ii, new RelUnion(ccdummy, new RelUnion(new RelComposition(icdummy, ccdummy), new RelComposition(ii, icdummy))), "ic");
            //cc := cc0 ∪ci ∪(ci;ic)∪(cc;cc)
            Relation cc= new RelUnion(cc0, new RelUnion(ci, new RelUnion(new RelComposition(ci, ic), new RelComposition(ccdummy, ccdummy))), "cc");
            //ppo := ((R × R) ∩ ii) ∪ ((R × W) ∩ ic)
            Relation RR=new BasicRelation("RR");
            Relation ppo=new RelUnion(new RelInterSect(ii, RR), new RelInterSect(RW, ic),"ppo");
            //fence := sync ∪(lwsync \(W×R)) 
            Relation sync=new BasicRelation("sync");
            Relation lwsync=new BasicRelation("lwsync");
            Relation fence=new RelUnion(sync, new RelMinus(lwsync, WR));
            //hb := ppo ∪fence ∪rfe
            Relation hb=new RelUnion(ppo, new RelUnion(fence, rfe),"hb");
            //prop-base := (fence ∪ (rfe; fence)); hb∗
            Relation propbase=new RelComposition(new RelUnion(fence, new RelComposition(rfe, fence)), new RelTransRef(hb), "propbase");
            //prop := ((W × W) ∩ prop-base) ∪ (com∗; prop-base∗; sync; hb∗)
            Relation WW=new BasicRelation("WW");
            Relation prop=new RelUnion(new RelInterSect(WW, propbase),new RelComposition(new RelTransRef(propbase), new RelComposition(sync, new RelTransRef(hb))),"prop");
            temp.addRel(ci);
            temp.addRel(cc);
            temp.addAxiom(new Acyclic(hb));
            temp.addAxiom(new Acyclic(new RelUnion(co, prop)));
            temp.addAxiom(new Irreflexive(new RelComposition(fre, new RelComposition(prop, new RelTransRef(hb)))));
            return temp;
        }
        return null;
    }

    protected Vector<Axiom> axioms = new Vector<>();
    private Vector<Relation> namedrels = new Vector<>();

    public void addAxiom(Axiom ax) {
        axioms.add(ax);
    }

    public void addRel(Relation rel) {
        namedrels.add(rel);
    }

    public BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        BoolExpr expr = ctx.mkTrue();
        Set<String> encodedRels=new HashSet<String>();
        for (Axiom ax : axioms) {
            expr = ctx.mkAnd(expr, ax.getRel().encode(program, ctx, encodedRels));
        }
        for (Relation namedrel : namedrels) {
            expr = ctx.mkAnd(expr, namedrel.encode(program, ctx, encodedRels));
        }
        
        //System.out.println("encoded rels: "+encodedRels.toString());
        return expr;
    }

    public BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr expr = ctx.mkTrue();
        for (Axiom ax : axioms) {
            expr = ctx.mkAnd(expr, ax.Consistent(events, ctx));
        }
        return expr;
    }

    public BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr expr = ctx.mkFalse();
        for (Axiom ax : axioms) {
            expr = ctx.mkOr(expr, ax.Inconsistent(events, ctx));
        }
        return expr;
    }

    public String write() {
        String temp = "";
        Set<Relation> named = new HashSet<>();
        for (Axiom axiom : axioms) {
            temp = temp + axiom.write() + "\n";
            named.addAll(axiom.getRel().getNamedRelations());
        }
        for (Relation relation : named) {
            temp = temp + relation.write() + "\n";
        }
        for (Relation namedrel : namedrels) {
            temp=temp+namedrel.write()+"\n";
        }
        return temp;
    }

}
