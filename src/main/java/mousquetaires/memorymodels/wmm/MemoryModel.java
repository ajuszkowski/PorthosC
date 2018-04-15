package mousquetaires.memorymodels.wmm;

import com.google.common.collect.ImmutableSet;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Event;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.memorymodels.axioms.Acyclic;
import mousquetaires.memorymodels.axioms.Axiom;
import mousquetaires.memorymodels.axioms.Irreflexive;
import mousquetaires.memorymodels.relations.old.*;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;


public class MemoryModel {

    private final Kind kind;
    private final ImmutableSet<Axiom> axioms;
    private final ImmutableSet<Relation> namedRelations;

    private MemoryModel(Kind kind, ImmutableSet<Axiom> axioms, ImmutableSet<Relation> namedRelations) {
        this.kind = kind;
        this.axioms = axioms;
        this.namedRelations = namedRelations;
    }

    public Kind getKind() {
        return kind;
    }

    public ImmutableSet<Axiom> getAxioms() {
        return axioms;
    }

    public ImmutableSet<Relation> getNamedRelations() {
        return namedRelations;
    }


    public List<BoolExpr> encode(XUnrolledProgram program, Context ctx) throws Z3Exception {
        List<BoolExpr> asserts = new ArrayList<>();
        Set<String> encodedRels=new HashSet<String>();
        for (Axiom ax : axioms) {
            asserts.add(ax.getRel().encode(program, ctx, encodedRels));
        }
        for (Relation namedRelation : getNamedRelations()) {
            asserts.add(namedRelation.encode(program, ctx, encodedRels));
        }

        //System.out.println("encoded rels: "+encodedRels.toString());
        return asserts;
    }

    public BoolExpr Consistent(XUnrolledProgram program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr expr = ctx.mkTrue();
        for (Axiom ax : axioms) {
            expr = ctx.mkAnd(expr, ax.Consistent(events, ctx));
        }
        return expr;
    }

    public BoolExpr Inconsistent(XUnrolledProgram program, Context ctx) throws Z3Exception {
        List<BoolExpr> asserts = new ArrayList<>();
        Set<Event> events = program.getMemEvents();
        BoolExpr expr = ctx.mkFalse();
        for (Axiom ax : axioms) {
            expr = ctx.mkOr(expr, ax.Inconsistent(events, ctx));
        }
        return expr;
    }


    // --

    public enum Kind {
        SC,
        TSO,
        PSO,
        RMO,
        Alpha,
        Power,
        ARM,
        ;

        public static Kind parse(String value) {
            switch (value.toLowerCase()) {
                case "sc":
                    return Kind.SC;
                case "tso":
                    return Kind.TSO;
                case "pso":
                    return Kind.PSO;
                case "rmo":
                    return Kind.RMO;
                case "alpha":
                    return Kind.Alpha;
                case "power":
                    return Kind.Power;
                case "arm":
                    return Kind.ARM;
                default:
                    return null;
            }
        }

        public boolean is(Kind other) {
            return this == other;
        }

        public MemoryModel createModel() {
            ImmutableSet.Builder<Axiom> axiomsBuilder = new ImmutableSet.Builder<>();
            ImmutableSet.Builder<Relation> namedRelationsBuilder = new ImmutableSet.Builder<>();
            Supplier<MemoryModel> buildModel = () -> new MemoryModel(this, axiomsBuilder.build(), namedRelationsBuilder.build());

            //basics:
            Relation co = new BasicRelation("co");
            Relation po = new BasicRelation("po");
            Relation fr = new BasicRelation("fr");
            Relation rf = new BasicRelation("rf");
            Relation com = new RelUnion(new RelUnion(co, fr), rf, "com");

            //sc:
            if (is(SC)) {
                Relation ghbsc = new RelUnion(po, com, "ghb-sc");
                axiomsBuilder.add(new Acyclic(ghbsc));
                return buildModel.get();
            }

            //tso:
            Relation poloc = new BasicRelation("poloc");
            Relation rfe = new BasicRelation("rfe");
            Relation WR = new BasicRelation("WR");
            if (is(TSO)) {
                Relation comtso = new RelUnion(new RelUnion(co, fr), rfe, "com-tso");
                Relation mfence = new BasicRelation("mfence");
                Relation potso = new RelUnion(new RelMinus(po, WR), mfence, "po-tso");
                Relation ghbtso = new RelUnion(potso, comtso, "ghb-tso");
                axiomsBuilder.add(new Acyclic(ghbtso));
                axiomsBuilder.add(new Acyclic(new RelUnion(poloc, com)));
                return buildModel.get();
            }

            if (is(Power)) {
                //acyclic((po ∩ sloc) ∪ rf ∪ fr ∪ co)
                //Relation sloc=new BasicRelation("sloc");
                axiomsBuilder.add(new Acyclic(new RelUnion(poloc, new RelUnion(new RelUnion(rf, fr), co))));
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
                Relation ci=new RelUnion(ci0, new RelUnion(new RelComposition(cidummy, ii), new RelComposition(ccdummy, cidummy)), "ci");
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
                Relation prop=new RelUnion(new RelInterSect(WW, propbase), new RelComposition(new RelTransRef(propbase), new RelComposition(sync, new RelTransRef(hb))), "prop");
                namedRelationsBuilder.add(ci);
                namedRelationsBuilder.add(cc);
                axiomsBuilder.add(new Acyclic(hb));
                axiomsBuilder.add(new Acyclic(new RelUnion(co, prop)));
                axiomsBuilder.add(new Irreflexive(new RelComposition(fre, new RelComposition(prop, new RelTransRef(hb)))));
                return buildModel.get();
            }

            // TODO: complete this method!
            throw new NotImplementedException("Not defined in old logic. TODO: complete this method!");
        }
    }
}
