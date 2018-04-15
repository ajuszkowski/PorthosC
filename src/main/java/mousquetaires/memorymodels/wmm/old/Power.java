package mousquetaires.memorymodels.wmm.old;

import java.util.Set;
import java.util.stream.Collectors;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;

import dartagnan.program.Event;
import dartagnan.program.LocalEvent;
import dartagnan.program.SharedMemEvent;
import dartagnan.program.Program;
import mousquetaires.memorymodels.EncodingsOld;
import mousquetaires.memorymodels.relations.old.Relation;
import mousquetaires.utils.Utils;

public class Power {

    public static BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        Set<Event> eventsL = program.getEvents().stream().filter(e -> e instanceof SharedMemEvent || e instanceof LocalEvent).collect(Collectors.toSet());

        BoolExpr enc = EncodingsOld.satUnion("co", "fr", events, ctx);
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("com", "(co+fr)", "rf", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("poloc", "com", events, ctx));

        enc = ctx.mkAnd(enc, EncodingsOld.satTransFixPoint("idd", eventsL, ctx));

        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("data", "idd^+", "RW", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satEmpty("addr", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("dp", "addr", "data", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("fre", "rfe", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("coe", "rfe", events, ctx));

        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("rdw", "poloc", "(fre;rfe)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("detour", "poloc", "(coe;rfe)", events, ctx));
        // Base case for program order
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("dp", "rdw", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("ii0", "(dp+rdw)", "rfi", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satEmpty("ic0", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("ci0", "ctrlisync", "detour", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("dp", "poloc", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("(dp+poloc)", "ctrl", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("addr", "po", events, ctx));
        enc = ctx.mkAnd(enc, satPowerPPO(events, ctx));

        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("cc0", "((dp+poloc)+ctrl)", "(addr;po)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("RR", "ii", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("RW", "ic", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("po-power", "(RR&ii)", "(RW&ic)", events, ctx));
        // Fences in Power
        enc = ctx.mkAnd(enc, EncodingsOld.satMinus("lwsync", "WR", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("fence-power", "sync", "(lwsync\\WR)", events, ctx));
        // Happens before
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("po-power", "rfe", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("hb-power", "(po-power+rfe)", "fence-power", events, ctx));
        // Prop-base
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("rfe", "fence-power", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("fence-power", "(rfe;fence-power)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satTransRef("hb-power", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("prop-base", "(fence-power+(rfe;fence-power))", "(hb-power)*", events, ctx));
        // Propagation for Power
        enc = ctx.mkAnd(enc, EncodingsOld.satTransRef("com", events, ctx));

        enc = ctx.mkAnd(enc, EncodingsOld.satTransRef("prop-base", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("(com)*", "(prop-base)*", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("((com)*;(prop-base)*)", "sync", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("(((com)*;(prop-base)*);sync)", "(hb-power)*", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satIntersection("WW", "prop-base", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("prop", "(WW&prop-base)", "((((com)*;(prop-base)*);sync);(hb-power)*)", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("fre", "prop", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satComp("(fre;prop)", "(hb-power)*", events, ctx));
        enc = ctx.mkAnd(enc, EncodingsOld.satUnion("co", "prop", events, ctx));
        return enc;
    }

    public static BoolExpr Consistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        return ctx.mkAnd(EncodingsOld.satAcyclic("hb-power", events, ctx),
                         EncodingsOld.satIrref("((fre;prop);(hb-power)*)", events, ctx),
                         EncodingsOld.satAcyclic("(co+prop)", events, ctx),
                         EncodingsOld.satAcyclic("(poloc+com)", events, ctx));
    }

    public static BoolExpr Inconsistent(Program program, Context ctx) throws Z3Exception {
        Set<Event> events = program.getMemEvents();
        BoolExpr enc = ctx.mkAnd(EncodingsOld.satCycleDef("hb-power", events, ctx),
                                 EncodingsOld.satCycleDef("(co+prop)", events, ctx),
                                 EncodingsOld.satCycleDef("(poloc+com)", events, ctx));
        enc = ctx.mkAnd(enc, ctx.mkOr(EncodingsOld.satCycle("hb-power", events, ctx),
                                      ctx.mkNot(EncodingsOld.satIrref("((fre;prop);(hb-power)*)", events, ctx)),
                                      EncodingsOld.satCycle("(co+prop)", events, ctx),
                                      EncodingsOld.satCycle("(poloc+com)", events, ctx)));
        return enc;
    }

    private static BoolExpr satPowerPPO(Set<Event> events, Context ctx) throws Z3Exception {
        BoolExpr enc = ctx.mkTrue();
        for (Event e1 : events) {
            for (Event e2 : events) {
                BoolExpr orClause1 = ctx.mkFalse();
                BoolExpr orClause2 = ctx.mkFalse();
                BoolExpr orClause3 = ctx.mkFalse();
                BoolExpr orClause4 = ctx.mkFalse();
                BoolExpr orClause5 = ctx.mkFalse();
                BoolExpr orClause6 = ctx.mkFalse();
                BoolExpr orClause7 = ctx.mkFalse();
                BoolExpr orClause8 = ctx.mkFalse();
                for (Event e3 : events) {
                    orClause1 = ctx.mkOr(orClause1, ctx.mkAnd(Utils.edge("ic", e1, e3, ctx), Utils.edge("ci", e3, e2, ctx)));
                    orClause2 = ctx.mkOr(orClause2, ctx.mkAnd(Utils.edge("ii", e1, e3, ctx), Utils.edge("ii", e3, e2, ctx)));
                    orClause3 = ctx.mkOr(orClause3, ctx.mkAnd(Utils.edge("ic", e1, e3, ctx), Utils.edge("cc", e3, e2, ctx)));
                    orClause4 = ctx.mkOr(orClause4, ctx.mkAnd(Utils.edge("ii", e1, e3, ctx), Utils.edge("ic", e3, e2, ctx)));
                    orClause5 = ctx.mkOr(orClause5, ctx.mkAnd(Utils.edge("ci", e1, e3, ctx), Utils.edge("ii", e3, e2, ctx)));
                    orClause6 = ctx.mkOr(orClause6, ctx.mkAnd(Utils.edge("cc", e1, e3, ctx), Utils.edge("ci", e3, e2, ctx)));
                    orClause7 = ctx.mkOr(orClause7, ctx.mkAnd(Utils.edge("ci", e1, e3, ctx), Utils.edge("ic", e3, e2, ctx)));
                    orClause8 = ctx.mkOr(orClause8, ctx.mkAnd(Utils.edge("cc", e1, e3, ctx), Utils.edge("cc", e3, e2, ctx)));
                }
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ic;ci", e1, e2, ctx), orClause1));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ii;ii", e1, e2, ctx), orClause2));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ic;cc", e1, e2, ctx), orClause3));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ii;ic", e1, e2, ctx), orClause4));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ci;ii", e1, e2, ctx), orClause5));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("cc;ci", e1, e2, ctx), orClause6));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ci;ic", e1, e2, ctx), orClause7));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("cc;cc", e1, e2, ctx), orClause8));

                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ii", e1, e2, ctx), ctx.mkOr(Utils.edge("ii0", e1, e2, ctx), Utils.edge("ci", e1, e2, ctx), Utils.edge("ic;ci", e1, e2, ctx), Utils.edge("ii;ii", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ic", e1, e2, ctx), ctx.mkOr(Utils.edge("ic0", e1, e2, ctx), Utils.edge("ii", e1, e2, ctx), Utils.edge("cc", e1, e2, ctx), Utils.edge("ic;cc", e1, e2, ctx), Utils.edge("ii;ic", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ci", e1, e2, ctx), ctx.mkOr(Utils.edge("ci0", e1, e2, ctx), Utils.edge("ci;ii", e1, e2, ctx), Utils.edge("cc;ci", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("cc", e1, e2, ctx), ctx.mkOr(Utils.edge("cc0", e1, e2, ctx), Utils.edge("ci", e1, e2, ctx), Utils.edge("ci;ic", e1, e2, ctx), Utils.edge("cc;cc", e1, e2, ctx))));

                if (Relation.Approx) {
//                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ii", e1, e2, ctx), ctx.mkOr(Utils.edge("ii0", e1, e2, ctx),
//                            Utils.edge("ci", e1, e2, ctx),
//                            Utils.edge("ic;ci", e1, e2, ctx),
//                            Utils.edge("ii;ii", e1, e2, ctx))));
//
//                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ic", e1, e2, ctx), ctx.mkOr(Utils.edge("ic0", e1, e2, ctx),
//                            Utils.edge("ii", e1, e2, ctx),
//                            Utils.edge("cc", e1, e2, ctx),
//                            Utils.edge("ic;cc", e1, e2, ctx),
//                            Utils.edge("ii;ic", e1, e2, ctx))));
//
//                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ci", e1, e2, ctx), ctx.mkOr(Utils.edge("ci0", e1, e2, ctx),
//                            Utils.edge("ci;ii", e1, e2, ctx),
//                            Utils.edge("cc;ci", e1, e2, ctx))));
//                            
//                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("cc", e1, e2, ctx), ctx.mkOr(Utils.edge("cc0", e1, e2, ctx),
//                            Utils.edge("ci", e1, e2, ctx), 
//                            Utils.edge("ci;ic", e1, e2, ctx), 
//                            Utils.edge("cc;cc", e1, e2, ctx))));

                } else {
                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ii", e1, e2, ctx), ctx.mkOr(ctx.mkAnd(Utils.edge("ii0", e1, e2, ctx), ctx.mkGt(Utils.intCount("ii", e1, e2, ctx), Utils.intCount("ii0", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ci", e1, e2, ctx), ctx.mkGt(Utils.intCount("ii", e1, e2, ctx), Utils.intCount("ci", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ic;ci", e1, e2, ctx), ctx.mkGt(Utils.intCount("ii", e1, e2, ctx), Utils.intCount("ic;ci", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ii;ii", e1, e2, ctx), ctx.mkGt(Utils.intCount("ii", e1, e2, ctx), Utils.intCount("ii;ii", e1, e2, ctx))))));

                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ic", e1, e2, ctx), ctx.mkOr(ctx.mkAnd(Utils.edge("ic0", e1, e2, ctx), ctx.mkGt(Utils.intCount("ic", e1, e2, ctx), Utils.intCount("ic0", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ii", e1, e2, ctx), ctx.mkGt(Utils.intCount("ic", e1, e2, ctx), Utils.intCount("ii", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("cc", e1, e2, ctx), ctx.mkGt(Utils.intCount("ic", e1, e2, ctx), Utils.intCount("cc", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ic;cc", e1, e2, ctx), ctx.mkGt(Utils.intCount("ic", e1, e2, ctx), Utils.intCount("ic;cc", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ii;ic", e1, e2, ctx), ctx.mkGt(Utils.intCount("ic", e1, e2, ctx), Utils.intCount("ii;ic", e1, e2, ctx))))));

                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ci", e1, e2, ctx), ctx.mkOr(ctx.mkAnd(Utils.edge("ci0", e1, e2, ctx), ctx.mkGt(Utils.intCount("ci", e1, e2, ctx), Utils.intCount("ci0", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ci;ii", e1, e2, ctx), ctx.mkGt(Utils.intCount("ci", e1, e2, ctx), Utils.intCount("ci;ii", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("cc;ci", e1, e2, ctx), ctx.mkGt(Utils.intCount("ci", e1, e2, ctx), Utils.intCount("cc;ci", e1, e2, ctx))))));

                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("cc", e1, e2, ctx), ctx.mkOr(ctx.mkAnd(Utils.edge("cc0", e1, e2, ctx), ctx.mkGt(Utils.intCount("cc", e1, e2, ctx), Utils.intCount("cc0", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ci", e1, e2, ctx), ctx.mkGt(Utils.intCount("cc", e1, e2, ctx), Utils.intCount("ci", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("ci;ic", e1, e2, ctx), ctx.mkGt(Utils.intCount("cc", e1, e2, ctx), Utils.intCount("ci;ic", e1, e2, ctx))),
                            ctx.mkAnd(Utils.edge("cc;cc", e1, e2, ctx), ctx.mkGt(Utils.intCount("cc", e1, e2, ctx), Utils.intCount("cc;cc", e1, e2, ctx))))));
                }
            }
        }
        return enc;
    }
}
