package mousquetaires.memorymodels.wmm;

import com.google.common.collect.ImmutableSet;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.syntax.xgraph.program.XProgram;
import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
import mousquetaires.memorymodels.axioms.ZAcyclicAxiom;
import mousquetaires.memorymodels.axioms.ZAxiom;
import mousquetaires.memorymodels.axioms.ZIrreflexiveAxiom;
import mousquetaires.memorymodels.relations.*;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;


public class MemoryModel {

    private final Kind kind;
    private final ImmutableSet<ZAxiom> axioms;
    private final ImmutableSet<ZRelation> namedRelations;

    private MemoryModel(Kind kind, ImmutableSet<ZAxiom> axioms, ImmutableSet<ZRelation> namedRelations) {
        this.kind = kind;
        this.axioms = axioms;
        this.namedRelations = namedRelations;
    }

    public Kind getKind() {
        return kind;
    }

    public ImmutableSet<ZAxiom> getAxioms() {
        return axioms;
    }

    public ImmutableSet<ZRelation> getNamedRelations() {
        return namedRelations;
    }


    public List<BoolExpr> encode(XProgram program, Context ctx) {
        List<BoolExpr> asserts = new ArrayList<>();
        Set<String> encodedRels=new HashSet<>();
        for (ZAxiom ax : axioms) {
            asserts.add(ax.getRel().encode(program, ctx, encodedRels));
        }
        for (ZRelation namedRelation : getNamedRelations()) {
            asserts.add(namedRelation.encode(program, ctx, encodedRels));
        }

        //System.out.println("encoded rels: "+encodedRels.toString());
        return asserts;
    }

    public BoolExpr Consistent(XProgram program, Context ctx) {
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        BoolExpr expr = ctx.mkTrue();
        for (ZAxiom ax : axioms) {
            expr = ctx.mkAnd(expr, ax.Consistent(events, ctx));
        }
        return expr;
    }

    public BoolExpr Inconsistent(XProgram program, Context ctx) {
        ImmutableSet<XSharedMemoryEvent> events = program.getSharedMemoryEvents();
        BoolExpr expr = ctx.mkFalse();
        for (ZAxiom ax : axioms) {
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
            ImmutableSet.Builder<ZAxiom> axiomsBuilder = new ImmutableSet.Builder<>();
            ImmutableSet.Builder<ZRelation> namedRelationsBuilder = new ImmutableSet.Builder<>();
            Supplier<MemoryModel> buildModel = () -> new MemoryModel(this, axiomsBuilder.build(), namedRelationsBuilder.build());

            //basics:
            ZRelation co = new ZBasicRelation("co");
            ZRelation po = new ZBasicRelation("po");
            ZRelation fr = new ZBasicRelation("fr");
            ZRelation rf = new ZBasicRelation("rf");
            ZRelation com = new ZRelUnion(new ZRelUnion(co, fr), rf, "com");

            //sc:
            if (is(SC)) {
                ZRelation ghbsc = new ZRelUnion(po, com, "ghb-sc");
                axiomsBuilder.add(new ZAcyclicAxiom(ghbsc));
                return buildModel.get();
            }

            //tso:
            ZRelation poloc = new ZBasicRelation("poloc");
            ZRelation rfe = new ZBasicRelation("rfe");
            ZRelation WR = new ZBasicRelation("WR");
            if (is(TSO)) {
                ZRelation comtso = new ZRelUnion(new ZRelUnion(co, fr), rfe, "com-tso");
                ZRelation mfence = new ZBasicRelation("mfence");
                ZRelation potso = new ZRelUnion(new ZRelMinus(po, WR), mfence, "po-tso");
                ZRelation ghbtso = new ZRelUnion(potso, comtso, "ghb-tso");
                axiomsBuilder.add(new ZAcyclicAxiom(ghbtso));
                axiomsBuilder.add(new ZAcyclicAxiom(new ZRelUnion(poloc, com)));
                return buildModel.get();
            }

            if (is(Power)) {
                //acyclic((po ∩ sloc) ∪ rf ∪ fr ∪ co)
                //ZRelation sloc=new ZBasicRelation("sloc");
                axiomsBuilder.add(new ZAcyclicAxiom(new ZRelUnion(poloc, new ZRelUnion(new ZRelUnion(rf, fr), co))));
                //ppo:
                //dp := ad ∪ dd
                //TODO: why is addr (ad) empty?
                ZRelation RW=new ZBasicRelation("RW");
                ZRelation dd = new ZRelInterSect(new ZRelLocTrans(new ZBasicRelation("idd")), RW);
                ZRelation dp = dd;
                //rdw := (po ∩ sloc) ∩ (fre;rfe):
                ZRelation fre=new ZBasicRelation("fre");
                ZRelation rdw = new ZRelInterSect(poloc, new ZRelComposition(fre, rfe));
                // detour := (po ∩ sloc) ∩ (coe;rfe)
                ZRelation detour = new ZRelInterSect(poloc, new ZRelComposition(new ZBasicRelation("coe"), rfe));
                //ii0 := dp ∪ rdw ∪ rfi
                ZRelation ii0=new ZRelUnion(dp, new ZRelUnion(rdw, new ZBasicRelation("rfi")));
                //ci0 := cd-isync ∪ detour
                ZRelation ci0=new ZRelUnion(new ZBasicRelation("ctrlisync"), detour);
                //cc0 :=dp∪(po∩sloc)∪cd∪(ad;po) addr is empty ?
                ZRelation cc0=new ZRelUnion(dp, new ZRelUnion(poloc, new ZBasicRelation("ctrl")));
                //ZRelation cc0=new ZRelUnion(dp, new ZRelUnion(poloc, new ZRelUnion(new ZBasicRelation("ctrl"), new ZRelComposition(ad, po))));
                //ii:=ii0 ∪ci∪(ic;ci)∪(ii;ii)
                ZRelation iidummy=new ZRelDummy("ii");
                ZRelation icdummy=new ZRelDummy("ic");
                ZRelation cidummy=new ZRelDummy("ci");
                ZRelation ccdummy=new ZRelDummy("cc");
                ZRelation ii=new ZRelUnion(ii0, new ZRelUnion(cidummy, new ZRelUnion(new ZRelComposition(icdummy, cidummy), new ZRelComposition(iidummy, iidummy))),"ii");
                //ci:=ci0 ∪(ci;ii)∪(cc;ci)
                ZRelation ci=new ZRelUnion(ci0, new ZRelUnion(new ZRelComposition(cidummy, ii), new ZRelComposition(ccdummy, cidummy)), "ci");
                //ic := ic0 ∪ii ∪cc ∪(ic;cc)∪(ii;ic)
                ZRelation ic=new ZRelUnion(ii, new ZRelUnion(ccdummy, new ZRelUnion(new ZRelComposition(icdummy, ccdummy), new ZRelComposition(ii, icdummy))), "ic");
                //cc := cc0 ∪ci ∪(ci;ic)∪(cc;cc)
                ZRelation cc= new ZRelUnion(cc0, new ZRelUnion(ci, new ZRelUnion(new ZRelComposition(ci, ic), new ZRelComposition(ccdummy, ccdummy))), "cc");
                //ppo := ((R × R) ∩ ii) ∪ ((R × W) ∩ ic)
                ZRelation RR=new ZBasicRelation("RR");
                ZRelation ppo=new ZRelUnion(new ZRelInterSect(ii, RR), new ZRelInterSect(RW, ic),"ppo");
                //fence := sync ∪(lwsync \(W×R))
                ZRelation sync=new ZBasicRelation("sync");
                ZRelation lwsync=new ZBasicRelation("lwsync");
                ZRelation fence=new ZRelUnion(sync, new ZRelMinus(lwsync, WR));
                //hb := ppo ∪fence ∪rfe
                ZRelation hb=new ZRelUnion(ppo, new ZRelUnion(fence, rfe),"hb");
                //prop-base := (fence ∪ (rfe; fence)); hb∗
                ZRelation propbase=new ZRelComposition(new ZRelUnion(fence, new ZRelComposition(rfe, fence)), new ZRelTransRef(hb), "propbase");
                //prop := ((W × W) ∩ prop-base) ∪ (com∗; prop-base∗; sync; hb∗)
                ZRelation WW=new ZBasicRelation("WW");
                ZRelation prop=new ZRelUnion(new ZRelInterSect(WW, propbase), new ZRelComposition(new ZRelTransRef(propbase), new ZRelComposition(sync, new ZRelTransRef(hb))), "prop");
                namedRelationsBuilder.add(ci);
                namedRelationsBuilder.add(cc);
                axiomsBuilder.add(new ZAcyclicAxiom(hb));
                axiomsBuilder.add(new ZAcyclicAxiom(new ZRelUnion(co, prop)));
                axiomsBuilder.add(new ZIrreflexiveAxiom(new ZRelComposition(fre, new ZRelComposition(prop, new ZRelTransRef(hb)))));
                return buildModel.get();
            }

            // TODO: complete this method!
            throw new NotImplementedException("Not defined in old logic. TODO: complete this method!");
        }
    }
}
