package porthosc.memorymodels;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.microsoft.z3.*;

import old.dartagnan.expression.AConst;
import old.dartagnan.program.Barrier;
import old.dartagnan.program.Event;
import old.dartagnan.program.InitEvent;
import old.dartagnan.program.Isb;
import old.dartagnan.program.Ish;
import old.dartagnan.program.Isync;
import old.dartagnan.program.LoadEvent;
import old.dartagnan.program.LocalEvent;
import old.dartagnan.program.Location;
import old.dartagnan.program.Lwsync;
import old.dartagnan.program.SharedMemEvent;
import old.dartagnan.program.Mfence;
import old.dartagnan.program.Program;
import old.dartagnan.program.Register;
import old.dartagnan.program.StoreEvent;
import old.dartagnan.program.Sync;
import porthosc.utils.Utils;

import static porthosc.utils.Utils.lastValueLoc;
import static porthosc.utils.Utils.lastValueReg;
import static porthosc.utils.Utils.ssaReg;

public class DomainOld {

    public static BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        BoolExpr enc = ctx.mkTrue();

        Set<Event> mEvents = program.getMemEvents();
        Set<Event> barriers = program.getEvents().stream().filter(e -> e instanceof Barrier).collect(Collectors.toSet());
        Set<Event> eventsL = program.getEvents().stream().filter(e -> e instanceof SharedMemEvent || e instanceof LocalEvent).collect(Collectors.toSet());

        for(Event e : eventsL) {
                enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ii", e, e, ctx)));
                enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ic", e, e, ctx)));
                enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ci", e, e, ctx)));
                enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("cc", e, e, ctx)));
        }

        for(Event e1 : mEvents) {
            for(Event e2 : mEvents) {
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("rf", e1, e2, ctx), ctx.mkAnd(e1.executes(ctx), e2.executes(ctx))));
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("co", e1, e2, ctx), ctx.mkAnd(e1.executes(ctx), e2.executes(ctx))));
                if(!(e1 instanceof InitEvent)) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("IM", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("IW", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("IR", e1, e2, ctx)));
                }
                else {
                    enc = ctx.mkAnd(enc, Utils.edge("IM", e1, e2, ctx));
                }
                if(!(e2 instanceof InitEvent)) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("MI", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("WI", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("RI", e1, e2, ctx)));
                }
                else {
                    enc = ctx.mkAnd(enc, Utils.edge("MI", e1, e2, ctx));
                }
                if(!(e1 instanceof LoadEvent)) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("RM", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("RW", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("RR", e1, e2, ctx)));
                }
                else {
                    enc = ctx.mkAnd(enc, Utils.edge("RM", e1, e2, ctx));
                }
                if(!(e2 instanceof LoadEvent)) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("MR", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("WR", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("RR", e1, e2, ctx)));
                }
                else {
                    enc = ctx.mkAnd(enc, Utils.edge("MR", e1, e2, ctx));
                }
                if(!(e1 instanceof StoreEvent || e1 instanceof InitEvent)) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("WM", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("WW", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("WR", e1, e2, ctx)));
                }
                else {
                    enc = ctx.mkAnd(enc, Utils.edge("WM", e1, e2, ctx));
                }
                if(!(e2 instanceof StoreEvent || e2 instanceof InitEvent)) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("MW", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("WW", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("RW", e1, e2, ctx)));

                }
                else {
                    enc = ctx.mkAnd(enc, Utils.edge("MW", e1, e2, ctx));
                }
                if(e1 instanceof LoadEvent && e2 instanceof LoadEvent) {
                    enc = ctx.mkAnd(enc, Utils.edge("RR", e1, e2, ctx));
                }
                if(e1 instanceof LoadEvent && (e2 instanceof InitEvent || e2 instanceof StoreEvent)) {
                    enc = ctx.mkAnd(enc, Utils.edge("RW", e1, e2, ctx));
                }
                if((e1 instanceof InitEvent || e1 instanceof StoreEvent) && (e2 instanceof InitEvent || e2 instanceof StoreEvent)) {
                    enc = ctx.mkAnd(enc, Utils.edge("WW", e1, e2, ctx));
                }
                if((e1 instanceof InitEvent || e1 instanceof StoreEvent) && e2 instanceof LoadEvent) {
                    enc = ctx.mkAnd(enc, Utils.edge("WR", e1, e2, ctx));
                }
                if(e1 == e2) {
                    enc = ctx.mkAnd(enc, Utils.edge("id", e1, e2, ctx));
                }
                else {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("id", e1, e2, ctx)));
                }
                if(e1.getMainThread() == e2.getMainThread()) {
                    enc = ctx.mkAnd(enc, Utils.edge("int", e1, e2, ctx));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ext", e1, e2, ctx)));
                    if(e1.getEId() < e2.getEId()) {
                        enc = ctx.mkAnd(enc, Utils.edge("po", e1, e2, ctx));
                        if(e1.getCondLevel() < e2.getCondLevel() && e1 instanceof LoadEvent && e2.getCondRegs().contains(e1.getReg())) {
                            enc = ctx.mkAnd(enc, Utils.edge("ctrl", e1, e2, ctx));
                        }
                        else {
                            enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ctrl", e1, e2, ctx)));
                        }
                    }
                    else {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("po", e1, e2, ctx)));
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ctrl", e1, e2, ctx)));
                    }
                    boolean noMfence = true;
                    boolean noSync = true;
                    boolean noLwsync = true;
                    boolean noIsync = true;
                    boolean noIsh = true;
                    boolean noIsb = true;
                    for(Event b : barriers.stream().filter(e -> e.getMainThread() == e1.getMainThread()
                                                            && e1.getEId() < e.getEId()
                                                            && e.getEId() < e2.getEId()).collect(Collectors.toSet())) {
                        if(b instanceof Mfence) {
                            noMfence = false;
                        }
                        if(b instanceof Sync) {
                            noSync = false;
                        }
                        if(b instanceof Lwsync) {
                            noLwsync = false;
                        }
                        if(b instanceof Isync) {
                            noIsync = false;
                        }
                        if(b instanceof Ish) {
                            noIsh = false;
                        }
                        if(b instanceof Isb) {
                            noIsb = false;
                        }
                    }
                    if(noMfence) {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("mfence", e1, e2, ctx)));
                    }
                    if(noSync) {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("sync", e1, e2, ctx)));
                    }
                    if(noLwsync) {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("lwsync", e1, e2, ctx)));
                    }
                    if(noIsync) {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("isync", e1, e2, ctx)));
                    }
                    if(noIsh) {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ish", e1, e2, ctx)));
                    }
                    if(noIsb) {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("isb", e1, e2, ctx)));
                    }
                }
                else {
                    enc = ctx.mkAnd(enc, Utils.edge("ext", e1, e2, ctx));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("int", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("po", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ctrl", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ii", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ic", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ci", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("cc", e1, e2, ctx)));
                }
                if(e1.getLoc() == e2.getLoc()) {
                    enc = ctx.mkAnd(enc, Utils.edge("loc", e1, e2, ctx));
                }
                else {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("loc", e1, e2, ctx)));
                }
                if(!((e1 instanceof StoreEvent || e1 instanceof InitEvent) && e2 instanceof LoadEvent && e1.getLoc() == e2.getLoc())) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("rf", e1, e2, ctx)));
                }
                if(!((e1 instanceof StoreEvent || e1 instanceof InitEvent) && (e2 instanceof StoreEvent || e2 instanceof InitEvent) && e1.getLoc() == e2.getLoc())) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("co", e1, e2, ctx)));
                }
                if(!(e1.getMainThread() == e2.getMainThread() && e1.getEId() < e2.getEId())) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("mfence", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("sync", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("lwsync", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("isync", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("ish", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("isb", e1, e2, ctx)));
                }
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("rfe", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("rf", e1, e2, ctx), Utils.edge("ext", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("rfi", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("rf", e1, e2, ctx), Utils.edge("int", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("coe", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("co", e1, e2, ctx), Utils.edge("ext", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("coi", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("co", e1, e2, ctx), Utils.edge("int", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("fre", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("fr", e1, e2, ctx), Utils.edge("ext", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("fri", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("fr", e1, e2, ctx), Utils.edge("int", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("poloc", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("po", e1, e2, ctx), Utils.edge("loc", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ctrlisync", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("ctrl", e1, e2, ctx), Utils.edge("isync", e1, e2, ctx))));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("ctrlisb", e1, e2, ctx),
                                        ctx.mkAnd(Utils.edge("ctrl", e1, e2, ctx), Utils.edge("isb", e1, e2, ctx))));
            }
        }

        for(Event e1 : mEvents) {
            for(Event e2 : mEvents) {
                BoolExpr mfences = ctx.mkFalse();
                BoolExpr syncs = ctx.mkFalse();
                BoolExpr lwsyncs = ctx.mkFalse();
                BoolExpr isyncs = ctx.mkFalse();
                BoolExpr ishs = ctx.mkFalse();
                BoolExpr isbs = ctx.mkFalse();

                for(Event b : barriers) {
                    if(b instanceof Mfence && e1.getMainThread() == b.getMainThread() && b.getMainThread() == e2.getMainThread()
                            && e1.getEId() < b.getEId() && b.getEId() < e2.getEId()) {
                        mfences = ctx.mkOr(mfences, b.executes(ctx));
                        enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), ctx.mkAnd(b.executes(ctx), e2.executes(ctx))),
                                Utils.edge("mfence", e1, e2, ctx)));
                    }
                    if(b instanceof Sync && e1.getMainThread() == b.getMainThread() && b.getMainThread() == e2.getMainThread()
                            && e1.getEId() < b.getEId() && b.getEId() < e2.getEId()) {
                        syncs = ctx.mkOr(syncs, b.executes(ctx));
                        enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), ctx.mkAnd(b.executes(ctx), e2.executes(ctx))),
                                Utils.edge("sync", e1, e2, ctx)));
                    }
                    if(b instanceof Lwsync && e1.getMainThread() == b.getMainThread() && b.getMainThread() == e2.getMainThread()
                            && e1.getEId() < b.getEId() && b.getEId() < e2.getEId()) {
                        lwsyncs = ctx.mkOr(lwsyncs, b.executes(ctx));
                        enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), ctx.mkAnd(b.executes(ctx), e2.executes(ctx))),
                                Utils.edge("lwsync", e1, e2, ctx)));
                    }
                    if(b instanceof Isync && e1.getMainThread() == b.getMainThread() && b.getMainThread() == e2.getMainThread()
                            && e1.getEId() < b.getEId() && b.getEId() < e2.getEId()) {
                        isyncs = ctx.mkOr(isyncs, b.executes(ctx));
                        enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), ctx.mkAnd(b.executes(ctx), e2.executes(ctx))),
                                Utils.edge("isync", e1, e2, ctx)));
                    }
                    if(b instanceof Ish && e1.getMainThread() == b.getMainThread() && b.getMainThread() == e2.getMainThread()
                            && e1.getEId() < b.getEId() && b.getEId() < e2.getEId()) {
                        ishs = ctx.mkOr(ishs, b.executes(ctx));
                        enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), ctx.mkAnd(b.executes(ctx), e2.executes(ctx))),
                                Utils.edge("ish", e1, e2, ctx)));
                    }
                    if(b instanceof Isb && e1.getMainThread() == b.getMainThread() && b.getMainThread() == e2.getMainThread()
                            && e1.getEId() < b.getEId() && b.getEId() < e2.getEId()) {
                        isbs = ctx.mkOr(isbs, b.executes(ctx));
                        enc = ctx.mkAnd(enc, ctx.mkImplies(ctx.mkAnd(e1.executes(ctx), ctx.mkAnd(b.executes(ctx), e2.executes(ctx))),
                                Utils.edge("isb", e1, e2, ctx)));
                    }
                }
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("mfence", e1, e2, ctx), mfences));
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("sync", e1, e2, ctx), syncs));
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("lwsync", e1, e2, ctx), lwsyncs));
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("isync", e1, e2, ctx), isyncs));
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("ish", e1, e2, ctx), ishs));
                enc = ctx.mkAnd(enc, ctx.mkImplies(Utils.edge("isb", e1, e2, ctx), isbs));
            }
        }

        for(Event e1 : eventsL) {
            for(Event e2 : eventsL) {
                if(e1.getMainThread() != e2.getMainThread() || e2.getEId() < e1.getEId() || e1 == e2) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("idd", e1, e2, ctx)));
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("data", e1, e2, ctx)));
                }
                if(e2 instanceof StoreEvent) {
                    if(!e2.getLastModMap().get(e2.getReg()).contains(e1)) {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("idd", e1, e2, ctx)));
                    }
                }
                if(e2 instanceof LoadEvent) {
                    if(!e2.getLastModMap().keySet().contains(e2.getLoc())) {
                        enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("idd", e1, e2, ctx)));
                    }
                }
                if(e2 instanceof LocalEvent && e2.getExpr() instanceof AConst) {
                    enc = ctx.mkAnd(enc, ctx.mkNot(Utils.edge("idd", e1, e2, ctx)));
                }
            }
        }

        for(Event e : eventsL) {
            if(e instanceof StoreEvent) {
                BoolExpr orClause = ctx.mkFalse();
                for(Event x : e.getLastModMap().get(e.getReg())) {
                    orClause = ctx.mkOr(orClause, Utils.edge("idd", x, e, ctx));
                }
                enc = ctx.mkAnd(enc, orClause);
            }
            if(e instanceof LoadEvent) {
                if(!e.getLastModMap().keySet().contains(e.getLoc())) {
                    continue;
                }
                BoolExpr orClause = ctx.mkFalse();
                for(Event x : e.getLastModMap().get(e.getLoc())) {
                    orClause = ctx.mkOr(orClause, Utils.edge("idd", x, e, ctx));
                }
                enc = ctx.mkAnd(enc, orClause);
            }
            if(e instanceof LocalEvent) {
                for(Register reg : e.getExpr().getRegs()) {
                    BoolExpr orClause = ctx.mkFalse();
                    for(Event x : e.getLastModMap().get(reg)) {
                        orClause = ctx.mkOr(orClause, Utils.edge("idd", x, e, ctx));
                    }
                    enc = ctx.mkAnd(enc, orClause);
                }
            }
        }

        for(Event e1 : mEvents) {
            for(Event e2 : mEvents) {
                BoolExpr orClause = ctx.mkFalse();
                for(Event e3 : mEvents) {
                    orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge("rf", e3, e1, ctx), Utils.edge("co", e3, e2, ctx)));
                }
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge("fr", e1, e2, ctx), orClause));
            }
        }

        Set<Location> locs = mEvents.stream().filter(e -> e instanceof SharedMemEvent).map(e -> e.getLoc()).collect(Collectors.toSet());
        for(Location loc : locs) {
            Set<Event> writesEventsLoc = mEvents.stream().filter(e -> (e instanceof StoreEvent || e instanceof InitEvent) && e.getLoc() == loc).collect(Collectors.toSet());
            enc = ctx.mkAnd(enc, EncodingsOld.satTO("co", writesEventsLoc, ctx));
        }

        for(Event e : mEvents.stream().filter(e -> e instanceof InitEvent).collect(Collectors.toSet())) {
            enc = ctx.mkAnd(enc, ctx.mkEq(Utils.intVar("co", e, ctx), ctx.mkInt(1)));
        }

        for(Event w1 : mEvents.stream().filter(e -> e instanceof InitEvent || e instanceof StoreEvent).collect(Collectors.toSet())) {
            Set<Event> writeSameLoc = mEvents.stream().filter(e -> (e instanceof InitEvent || e instanceof StoreEvent) && w1.getLoc() == e.getLoc()).collect(Collectors.toSet());
            BoolExpr lastCoOrder = w1.executes(ctx);
            for(Event w2 : writeSameLoc) {
                lastCoOrder = ctx.mkAnd(lastCoOrder, ctx.mkNot(Utils.edge("co", w1, w2, ctx)));
            }
            enc = ctx.mkAnd(enc, ctx.mkImplies(lastCoOrder, ctx.mkEq(lastValueLoc(w1.getLoc(), ctx), ((SharedMemEvent) w1).ssaLoc)));
        }

        for(Event r1 : eventsL.stream().filter(e -> e instanceof LoadEvent || e instanceof LocalEvent).collect(Collectors.toSet())) {
            Set<Event> modRegLater = eventsL.stream().filter(e -> (e instanceof LoadEvent || e instanceof LocalEvent) && r1.getReg() == e.getReg() && r1.getEId() < e.getEId()).collect(Collectors.toSet());
            BoolExpr lastModReg = r1.executes(ctx);
            for(Event r2 : modRegLater) {
                lastModReg = ctx.mkAnd(lastModReg, ctx.mkNot(r2.executes(ctx)));
            }
            enc = ctx.mkAnd(enc, ctx.mkImplies(lastModReg, ctx.mkEq(lastValueReg(r1.getReg(), ctx), ssaReg(r1.getReg(), r1.getSsaRegIndex(), ctx))));
        }

        for(Event e : mEvents.stream().filter(e -> e instanceof LoadEvent).collect(Collectors.toSet())) {
            Set<Event> storeEventsLoc = mEvents.stream().filter(x -> (x instanceof StoreEvent || x instanceof InitEvent) && e.getLoc() == x.getLoc()).collect(Collectors.toSet());
            Set<BoolExpr> rfPairs = new HashSet<BoolExpr>();
            for(Event w : storeEventsLoc) {
                rfPairs.add(Utils.edge("rf", w, e, ctx));
            }
            enc = ctx.mkAnd(enc, ctx.mkImplies(e.executes(ctx), EncodingsOld.encodeEO(rfPairs, ctx)));
        }
        return enc;
    }
}