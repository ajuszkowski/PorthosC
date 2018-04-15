package mousquetaires.memorymodels;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.memorymodels.relations.ZRelation;
import mousquetaires.utils.Utils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Encodings {

    public static BoolExpr satComp(String name, String r1, String r2, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            for (XEvent e2 : events) {
                BoolExpr orClause = ctx.mkFalse();
                for (XEvent e3 : events) {
                    orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge(r1, e1, e3, ctx), Utils.edge(r2, e3, e2, ctx)));
                }
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(name, e1, e2, ctx), orClause));
            }
        }
        return enc;
    }

    public static BoolExpr satComp(String r1, String r2, Set<? extends XEvent> events, Context ctx) {
        String name = String.format("(%s;%s)", r1, r2);
        return satComp(name, r1, r2, events, ctx);
    }

    public static BoolExpr satEmpty(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            for (XEvent e2 : events) {
                enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge(name, e1, e2, ctx)));
            }
        }
        return enc;
    }

    public static BoolExpr satUnion(String name, String r1, String r2, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            for (XEvent e2 : events) {
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(name, e1, e2, ctx),
                                              ctx.mkOr(Utils.edge(r1, e1, e2, ctx), Utils.edge(r2, e1, e2, ctx))));
            }
        }
        return enc;
    }

    public static BoolExpr satUnion(String r1, String r2, Set<? extends XEvent> events, Context ctx) {
        String name = String.format("(%s+%s)", r1, r2);
        return satUnion(name, r1, r2, events, ctx);
    }

    public static BoolExpr satIntersection(String name, String r1, String r2, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            for (XEvent e2 : events) {
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(name, e1, e2, ctx),
                                              ctx.mkAnd(Utils.edge(r1, e1, e2, ctx), Utils.edge(r2, e1, e2, ctx))));
            }
        }
        return enc;
    }

    public static BoolExpr satIntersection(String r1, String r2, Set<? extends XEvent> events, Context ctx) {
        String name = String.format("(%s&%s)", r1, r2);
        return satIntersection(name, r1, r2, events, ctx);
    }

    public static BoolExpr satMinus(String name, String r1, String r2, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            for (XEvent e2 : events) {
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(name, e1, e2, ctx), ctx.mkAnd(Utils.edge(r1, e1, e2, ctx),
                                                                                       ctx.mkNot(Utils.edge(r2, e1, e2,
                                                                                                            ctx)))));
            }
        }
        return enc;
    }

    public static BoolExpr satMinus(String r1, String r2, Set<? extends XEvent> events, Context ctx) {
        String name = String.format("(%s\\%s)", r1, r2);
        return satMinus(name, r1, r2, events, ctx);
    }

    public static BoolExpr satTO(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            enc = ctx.mkAnd(enc, ctx.mkImplies(e1.executes(ctx), ctx.mkGt(Utils.intVar(name, e1, ctx), ctx.mkInt(0))));
            enc = ctx.mkAnd(enc, ctx.mkImplies(e1.executes(ctx),
                                               ctx.mkLe(Utils.intVar(name, e1, ctx), ctx.mkInt(events.size()))));
            for (XEvent e2 : events) {
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge(name, e1, e2, ctx),
                                                   ctx.mkLt(Utils.intVar(name, e1, ctx), Utils.intVar(name, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), e2.executes(ctx)),
                                                   ctx.mkImplies(ctx.mkLt(Utils.intVar(name, e1, ctx),
                                                                          Utils.intVar(name, e2, ctx)),
                                                                 Utils.edge(name, e1, e2, ctx))));
                if (e1 != e2) {
                    enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), e2.executes(ctx)),
                                                       ctx.mkNot(ctx.mkEq(Utils.intVar(name, e1, ctx),
                                                                          Utils.intVar(name, e2, ctx)))));
                    enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), e2.executes(ctx)),
                                                       ctx.mkOr(Utils.edge(name, e1, e2, ctx),
                                                                Utils.edge(name, e2, e1, ctx))));
                }
            }
        }
        return enc;
    }

    public static BoolExpr satAcyclic(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            enc = ctx.mkAnd(enc, ctx.mkImplies(e1.executes(ctx), ctx.mkGt(Utils.intVar(name, e1, ctx), ctx.mkInt(0))));
            for (XEvent e2 : events) {
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge(name, e1, e2, ctx),
                                                   ctx.mkLt(Utils.intVar(name, e1, ctx), Utils.intVar(name, e2, ctx))));
            }
        }
        return enc;
    }

    public static BoolExpr satCycle(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr oneXEventInCycle = ctx.mkFalse();
        for (XEvent e : events) {
            oneXEventInCycle = ctx.mkOr(oneXEventInCycle, Utils.cycleVar(name, e, ctx));
        }
        return oneXEventInCycle;
    }

    public static BoolExpr satCycleDef(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            Set<BoolExpr> source = new HashSet<>();
            Set<BoolExpr> target = new HashSet<>();
            for (XEvent e2 : events) {
                source.add(Utils.cycleEdge(name, e1, e2, ctx));
                target.add(Utils.cycleEdge(name, e2, e1, ctx));
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.cycleEdge(name, e1, e2, ctx),
                                                   ctx.mkAnd(e1.executes(ctx), e2.executes(ctx),
                                                             Utils.edge(name, e1, e2, ctx),
                                                             Utils.cycleVar(name, e1, ctx),
                                                             Utils.cycleVar(name, e2, ctx))));
            }
            enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.cycleVar(name, e1, ctx),
                                               ctx.mkAnd(encodeEO(source, ctx), encodeEO(target, ctx))));
        }
        return enc;
    }

    public static BoolExpr satTransFixPoint(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        int bound = (int) (Math.ceil(Math.log(events.size())) + 1);
        for (XEvent e1 : events) {
            for (XEvent e2 : events) {
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(String.format("%s0", name), e1, e2, ctx),
                                              Utils.edge(name, e1, e2, ctx)));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(String.format("%s^+", name), e1, e2, ctx),
                                              Utils.edge(String.format("%s%s", name, bound), e1, e2, ctx)));
            }
        }
        for (int i : IntStream.range(0, bound).toArray()) {
            for (XEvent e1 : events) {
                for (XEvent e2 : events) {
                    BoolExpr orClause = ctx.mkFalse();
                    for (XEvent e3 : events) {
                        orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge(String.format("%s%s", name, i), e1, e3, ctx),
                                                                Utils.edge(String.format("%s%s", name, i), e3, e2,
                                                                           ctx)));
                    }
                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(String.format("%s%s", name, i + 1), e1, e2, ctx),
                                                  ctx.mkOr(Utils.edge(name, e1, e2, ctx), orClause)));
                }
            }
        }
        return enc;
    }

    public static BoolExpr satTransIDL(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e1 : events) {
            for (XEvent e2 : events) {
                BoolExpr orClause = ctx.mkFalse();
                for (XEvent e3 : events) {
                    orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge(String.format("%s^+", name), e1, e3, ctx),
                                                            Utils.edge(String.format("%s^+", name), e3, e2, ctx),
                                                            ctx.mkGt(Utils.intCount(
                                                                    String.format("(%s^+;%s^+)", name, name), e1, e2,
                                                                    ctx),
                                                                     Utils.intCount(String.format("%s^+", name), e1, e3,
                                                                                    ctx)),
                                                            ctx.mkGt(Utils.intCount(
                                                                    String.format("(%s^+;%s^+)", name, name), e1, e2,
                                                                    ctx),
                                                                     Utils.intCount(String.format("%s^+", name), e3, e2,
                                                                                    ctx))));
                }
                enc = ctx.mkAnd(enc,
                                ctx.mkEq(Utils.edge(String.format("(%s^+;%s^+)", name, name), e1, e2, ctx), orClause));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(String.format("%s^+", name), e1, e2, ctx), ctx.mkOr(
                        ctx.mkAnd(Utils.edge(name, e1, e2, ctx),
                                  ctx.mkGt(Utils.intCount(String.format("%s^+", name), e1, e2, ctx),
                                           Utils.intCount(name, e1, e2, ctx))),
                        ctx.mkAnd(Utils.edge(String.format("(%s^+;%s^+)", name, name), e1, e2, ctx),
                                  ctx.mkGt(Utils.intCount(String.format("%s^+", name), e1, e2, ctx),
                                           Utils.intCount(String.format("(%s^+;%s^+)", name, name), e1, e2, ctx))))));

            }
        }
        return enc;
    }

    public static BoolExpr encodeEO(Set<BoolExpr> set, Context ctx) {
        BoolExpr enc = ctx.mkFalse();
        for (BoolExpr exp : set) {
            BoolExpr thisYesOthersNot = exp;
            for (BoolExpr x : set.stream().filter(x -> x != exp).collect(Collectors.toSet())) {
                thisYesOthersNot = ctx.mkAnd(thisYesOthersNot, ctx.mkNot(x));
            }
            enc = ctx.mkOr(enc, thisYesOthersNot);
        }
        return enc;
    }

    public static BoolExpr encodeALO(Set<BoolExpr> set, Context ctx) {
        BoolExpr enc = ctx.mkFalse();
        for (BoolExpr exp : set) {
            enc = ctx.mkOr(enc, exp);
        }
        return enc;
    }

    public static BoolExpr satTransRef(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc;
        if (ZRelation.Approx) {
            enc = ctx.mkTrue();
            for (XEvent e1 : events) {
                for (XEvent e2 : events) {
                    //transitive
                    BoolExpr orClause = ctx.mkFalse();
                    for (XEvent e3 : events) {
                        orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge(String.format("%s^+", name), e1, e3, ctx),
                                                                Utils.edge(String.format("%s^+", name), e3, e2, ctx)));
                    }
                    //original relation
                    orClause = ctx.mkOr(orClause, Utils.edge(name, e1, e2, ctx));
                    //putting it together:
                    enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(String.format("%s^+", name), e1, e2, ctx), orClause));

                }
            }
        }
        else {
            enc = satTransFixPoint(name, events, ctx);
        }
        enc = ctx.mkAnd(enc, satUnion(String.format("(%s)*", name), "id", String.format("%s^+", name), events, ctx));
        return enc;
    }

    public static BoolExpr satTransRefIDL(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = satTransIDL(name, events, ctx);
        enc = ctx.mkAnd(enc, satUnion(String.format("(%s)*", name), "id", String.format("%s^+", name), events, ctx));
        return enc;
    }

    public static BoolExpr satTransRef2(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e : events) {
            enc = ctx.mkAnd(enc, Utils.edge(String.format("(%s)", name), e, e, ctx));
        }
        for (XEvent e1 : events) {
            for (XEvent e2 : events) {
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge(name, e1, e2, ctx),
                                                   Utils.edge(String.format("(%s)*", name), e1, e2, ctx)));
                BoolExpr orClause = ctx.mkFalse();
                for (XEvent e3 : events) {
                    orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge(String.format("(%s)*", name), e1, e3, ctx),
                                                            Utils.edge(String.format("(%s)*", name), e3, e2, ctx)));
                }
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(String.format("(%s)*", name), e1, e2, ctx), orClause));
            }
        }
        return enc;
    }

    public static BoolExpr satIrref(String name, Set<? extends XEvent> events, Context ctx) {
        BoolExpr enc = ctx.mkTrue();
        for (XEvent e : events) {
            enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge(name, e, e, ctx)));
        }
        return enc;
    }

    //public static BoolExpr encodeCommonExecutions(Program p1, Program p2, Context ctx) {
    //    BoolExpr enc = ctx.mkTrue();
    //    Set<XEvent> lXEventsP1 = p1.getXEvents().stream().filter(e -> e instanceof SharedMemXEvent | e instanceof LocalXEvent).collect(Collectors.toSet());
    //    Set<XEvent> lXEventsP2 = p2.getXEvents().stream().filter(e -> e instanceof SharedMemXEvent | e instanceof LocalXEvent).collect(Collectors.toSet());
    //    Set<XEvent> rXEventsP1 = p1.getXEvents().stream().filter(e -> e instanceof LoadXEvent).collect(Collectors.toSet());
    //    Set<XEvent> wXEventsP1 = p1.getXEvents().stream().filter(e -> e instanceof StoreXEvent || e instanceof InitXEvent).collect(Collectors.toSet());
    //    Set<XEvent> rXEventsP2 = p2.getXEvents().stream().filter(e -> e instanceof LoadXEvent).collect(Collectors.toSet());
    //    Set<XEvent> wXEventsP2 = p2.getXEvents().stream().filter(e -> e instanceof StoreXEvent || e instanceof InitXEvent).collect(Collectors.toSet());
    //    for(XEvent e1 : lXEventsP1) {
    //        for(XEvent e2 : lXEventsP2) {
    //            if(e1.getHLId().equals(e2.getHLId())) {
    //                enc = ctx.mkAnd(enc, ctx.mkEq(e1.executes(ctx), e2.executes(ctx)));
    //            }
    //        }
    //    }
    //    for(XEvent r1 : rXEventsP1) {
    //        for(XEvent r2 : rXEventsP2) {
    //            if(r1.getHLId().equals(r2.getHLId())) {
    //                for(XEvent w1 : wXEventsP1) {
    //                    for(XEvent w2 : wXEventsP2) {
    //                        if(r1.getLoc() != w1.getLoc()) {continue;}
    //                        if(r2.getLoc() != w2.getLoc()) {continue;}
    //                        if(w1.getHLId().equals(w2.getHLId())) {
    //                            enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("rf", w1, r1, ctx), Utils.edge("rf", w2, r2, ctx)));
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //    }
    //    for(XEvent w1P1 : wXEventsP1) {
    //        for(XEvent w1P2 : wXEventsP2) {
    //            if(w1P1.getHLId().equals(w1P2.getHLId())) {
    //                for(XEvent w2P1 : wXEventsP1) {
    //                    for(XEvent w2P2 : wXEventsP2) {
    //                        if(w1P1.getLoc() != w2P1.getLoc()) {continue;}
    //                        if(w1P1.getLoc() != w2P2.getLoc()) {continue;}
    //                        if(w1P1 == w2P1 | w1P2 == w2P2) {continue;}
    //                        if(w2P1.getHLId().equals(w2P2.getHLId())) {
    //                            enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("co", w1P1, w2P1, ctx), Utils.edge("co", w1P2, w2P2, ctx)));
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //    }
    //    return enc;
    //}

    //public static BoolExpr encodePreserveFences(Program p1, Program p2, Context ctx) {
    //    BoolExpr enc = ctx.mkTrue();
    //    Set<XEvent> memXEventsP1 = p1.getMemXEvents();
    //    Set<XEvent> memXEventsP2 = p2.getMemXEvents();
    //    for(XEvent e1P1 : memXEventsP1) {
    //        for(XEvent e1P2 : memXEventsP2) {
    //            if(e1P1.getHLId().equals(e1P2.getHLId())) {
    //                for(XEvent e2P1 : memXEventsP1) {
    //                    for(XEvent e2P2 : memXEventsP2) {
    //                        if(e1P1.getProcessId() != e2P1.getProcessId()) {continue;}
    //                        if(e1P2.getProcessId() != e2P2.getProcessId()) {continue;}
    //                        if(e1P1.getEId() >= e2P1.getEId() || e1P2.getEId() >= e2P2.getEId()) {continue;}
    //                        if(e2P1.getHLId().equals(e2P2.getHLId())) {
    //                            enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("sync", e1P1, e2P1, ctx), Utils.edge("sync", e1P2, e2P2, ctx)));
    //                            enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("lwsync", e1P1, e2P1, ctx), ctx.mkOr(Utils.edge("lwsync", e1P2, e2P2, ctx), Utils.edge("sync", e1P2, e2P2, ctx))));
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //    }
    //    return enc;
    //}
    //
    //public static BoolExpr encodeReachedState(Program p, Model model, Context ctx) {
    //    Set<Location> locs = p.getXEvents().stream().filter(e -> e instanceof SharedMemXEvent).map(e -> e.getLoc()).collect(Collectors.toSet());
    //    BoolExpr reachedState = ctx.mkTrue();
    //    for(Location loc : locs) {
    //        reachedState = ctx.mkAnd(reachedState, ctx.mkEq(lastValueLoc(loc, ctx), model.getConstInterp(lastValueLoc(loc, ctx))));
    //    }
    //    Set<XEvent> executedXEvents = p.getXEvents().stream().filter(e -> model.getConstInterp(e.executes(ctx)).isTrue()).collect(Collectors.toSet());
    //    Set<Register> regs = executedXEvents.stream().filter(e -> e instanceof LocalXEvent | e instanceof LoadXEvent).map(e -> e.getReg()).collect(Collectors.toSet());
    //    for(Register reg : regs) {
    //        reachedState = ctx.mkAnd(reachedState, ctx.mkEq(lastValueReg(reg, ctx), model.getConstInterp(lastValueReg(reg, ctx))));
    //    }
    //    return reachedState;
    //}

}