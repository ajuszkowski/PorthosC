package mousquetaires.languages.syntax.xrepr;

import com.google.common.collect.ImmutableSet;


public class XProgram {

    private ImmutableSet<XProcess> processes;

    public XProgram(ImmutableSet<XProcess> processes) {
        this.processes = processes;
    }


    //public String name;
    //public Assert ass;
    //private List<XProcess> processes;
    //public MapSSA lastMap;

    //public XProgram(String name) {
    //    this.name = name;
    //    this.processes = new ArrayList<>();
    //}

    //public void add(XProcess t) {
    //    processes.add(t);
    //}
    //
    //public String toString() {
    //
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    String output = name + "\n";
    //    while (iter.hasNext()) {
    //        XProcess next = iter.next();
    //        if(next instanceof Init) {
    //            continue;
    //        }
    //        output = output.concat(String.format("\nthread %d\n%s\n", iter.nextIndex(), next));
    //    }
    //    return output;
    //}
    //
    //public XProgram clone() {
    //    List<XProcess> newProcesses = new ArrayList<XProcess>();
    //
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        newProcesses.add(t.clone());
    //    }
    //    XProgram newP = new XProgram(name);
    //    newP.setProcesses(newProcesses);
    //    return newP;
    //}
    //
    //public void initialize() {
    //    initialize(1);
    //}
    //
    //public void initialize(int steps) {
    //    List<XProcess> unrolledProcesses = new ArrayList<XProcess>();
    //
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        // TODO: find out why the null thread appears here, see tests DartagnanBurnsTests.test_burns_litmus_rx and DartagnanLamportTests.test_lamport_litmus_rx
    //        t = t.unroll(steps);
    //        unrolledProcesses.add(t);
    //    }
    //    processes = unrolledProcesses;
    //
    //    Set<XMemoryUnit> locs = getEvents().stream().filter(e -> e instanceof MemEvent).map(e -> ((MemEvent) e).getLoc()).collect(Collectors.toSet());
    //    for(XMemoryUnit loc : locs) {
    //        processes.add(new Init(loc));
    //    }
    //}
    //
    //public void compile(String target, boolean ctrl, boolean leading) {
    //    List<XProcess> compiledProcesses = new ArrayList<XProcess>();
    //
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t = t.compile(target, ctrl, leading);
    //        compiledProcesses.add(t);
    //    }
    //    processes = compiledProcesses;
    //
    //    setTId();
    //    setEId();
    //    setMainThread();
    //
    //    // Set the thread for the registers
    //    iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t.setCondRegs(new HashSet<Register>());
    //        t.setLastModMap(new LastModMap());
    //        Set<Register> regs = t.getEvents().stream().filter(e -> e instanceof Load).map(e -> ((Load) e).getReg()).collect(Collectors.toSet());
    //        regs.addAll(t.getEvents().stream().filter(e -> e instanceof Store).map(e -> ((Store) e).getReg()).collect(Collectors.toSet()));
    //        regs.addAll(t.getEvents().stream().filter(e -> e instanceof Local).map(e -> ((Local) e).getReg()).collect(Collectors.toSet()));
    //        for(Register reg : regs) {
    //            reg.setMainThread(t.tid);
    //        }
    //    }
    //}
    //
    //public void compile(String target, boolean ctrl, boolean leading, Integer firstEid) {
    //    List<XProcess> compiledProcesses = new ArrayList<XProcess>();
    //
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t = t.compile(target, ctrl, leading);
    //        compiledProcesses.add(t);
    //    }
    //    processes = compiledProcesses;
    //
    //    setTId();
    //    setEId(firstEid);
    //    setMainThread();
    //
    //    // Set the thread for the registers
    //    iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t.setCondRegs(new HashSet<Register>());
    //        t.setLastModMap(new LastModMap());
    //        Set<Register> regs = t.getEvents().stream().filter(e -> e instanceof Load).map(e -> ((Load) e).getReg()).collect(Collectors.toSet());
    //        regs.addAll(t.getEvents().stream().filter(e -> e instanceof Store).map(e -> ((Store) e).getReg()).collect(Collectors.toSet()));
    //        regs.addAll(t.getEvents().stream().filter(e -> e instanceof Local).map(e -> ((Local) e).getReg()).collect(Collectors.toSet()));
    //        for(Register reg : regs) {
    //            reg.setMainThread(t.tid);
    //        }
    //    }
    //}
    //
    //public void optCompile(Integer firstEId, boolean ctrl, boolean leading) {
    //    List<XProcess> compiledProcesses = new ArrayList<XProcess>();
    //
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t = t.optCompile(ctrl, leading);
    //        compiledProcesses.add(t);
    //    }
    //    processes = compiledProcesses;
    //
    //    setTId();
    //    setEId(firstEId);
    //    setMainThread();
    //
    //    // Set the thread for the registers
    //    iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t.setCondRegs(new HashSet<Register>());
    //        t.setLastModMap(new LastModMap());
    //        Set<Register> regs = t.getEvents().stream().filter(e -> e instanceof Load).map(e -> ((Load) e).getReg()).collect(Collectors.toSet());
    //        regs.addAll(t.getEvents().stream().filter(e -> e instanceof Store).map(e -> ((Store) e).getReg()).collect(Collectors.toSet()));
    //        regs.addAll(t.getEvents().stream().filter(e -> e instanceof Local).map(e -> ((Local) e).getReg()).collect(Collectors.toSet()));
    //        for(Register reg : regs) {
    //            reg.setMainThread(t.tid);
    //        }
    //    }
    //}
    //
    //public void allCompile(Integer firstEId) {
    //    List<XProcess> compiledProcesses = new ArrayList<XProcess>();
    //
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t = t.allCompile();
    //        compiledProcesses.add(t);
    //    }
    //    processes = compiledProcesses;
    //
    //    setTId();
    //    setEId(firstEId);
    //    setMainThread();
    //
    //    // Set the thread for the registers
    //    iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t.setCondRegs(new HashSet<Register>());
    //        t.setLastModMap(new LastModMap());
    //        Set<Register> regs = t.getEvents().stream().filter(e -> e instanceof Load).map(e -> ((Load) e).getReg()).collect(Collectors.toSet());
    //        regs.addAll(t.getEvents().stream().filter(e -> e instanceof Store).map(e -> ((Store) e).getReg()).collect(Collectors.toSet()));
    //        regs.addAll(t.getEvents().stream().filter(e -> e instanceof Local).map(e -> ((Local) e).getReg()).collect(Collectors.toSet()));
    //        for(Register reg : regs) {
    //            reg.setMainThread(t.tid);
    //        }
    //    }
    //}
    //
    //public BoolExpr encodeMM(Context ctx, String mcm) {
    //    BoolExpr enc = ctx.mkTrue();
    //    switch (mcm){
    //    case "sc":
    //        enc = ctx.mkAnd(enc, SC.encode(this, ctx));
    //        break;
    //    case "tso":
    //        enc = ctx.mkAnd(enc, TSO.encode(this, ctx));
    //        break;
    //    case "pso":
    //        enc = ctx.mkAnd(enc, PSO.encode(this, ctx));
    //        break;
    //    case "rmo":
    //        enc = ctx.mkAnd(enc, RMO.encode(this, ctx));
    //        break;
    //    case "alpha":
    //        enc = ctx.mkAnd(enc, Alpha.encode(this, ctx));
    //        break;
    //    case "power":
    //        enc = ctx.mkAnd(enc, Power.encode(this, ctx));
    //        break;
    //    case "arm":
    //        enc = ctx.mkAnd(enc, ARM.encode(this, ctx));
    //        break;
    //    default:
    //        System.out.println("Check encodeConsistent!");
    //        break;
    //    }
    //    return enc;
    //}
    //
    //public BoolExpr encodeConsistent(Context ctx, String mcm) {
    //    BoolExpr enc = ctx.mkTrue();
    //    switch (mcm){
    //    case "sc":
    //        enc = ctx.mkAnd(enc, SC.Consistent(this, ctx));
    //        break;
    //    case "tso":
    //        enc = ctx.mkAnd(enc, TSO.Consistent(this, ctx));
    //        break;
    //    case "pso":
    //        enc = ctx.mkAnd(enc, PSO.Consistent(this, ctx));
    //        break;
    //    case "rmo":
    //        enc = ctx.mkAnd(enc, RMO.Consistent(this, ctx));
    //        break;
    //    case "alpha":
    //        enc = ctx.mkAnd(enc, Alpha.Consistent(this, ctx));
    //        break;
    //    case "power":
    //        enc = ctx.mkAnd(enc, Power.Consistent(this, ctx));
    //        break;
    //    case "arm":
    //        enc = ctx.mkAnd(enc, ARM.Consistent(this, ctx));
    //        break;
    //    default:
    //        System.out.println("Check encodeConsistent!");
    //        break;
    //    }
    //    return enc;
    //}
    //
    //public BoolExpr encodeInconsistent(Context ctx, String mcm) {
    //    BoolExpr enc = ctx.mkTrue();
    //    switch (mcm) {
    //    case "sc":
    //        enc = ctx.mkAnd(enc, SC.Inconsistent(this, ctx));
    //        break;
    //    case "tso":
    //        enc = ctx.mkAnd(enc, TSO.Inconsistent(this, ctx));
    //        break;
    //    case "pso":
    //        enc = ctx.mkAnd(enc, PSO.Inconsistent(this, ctx));
    //        break;
    //    case "rmo":
    //        enc = ctx.mkAnd(enc, RMO.Inconsistent(this, ctx));
    //        break;
    //    case "alpha":
    //        enc = ctx.mkAnd(enc, Alpha.Inconsistent(this, ctx));
    //        break;
    //    case "power":
    //        enc = ctx.mkAnd(enc, Power.Inconsistent(this, ctx));
    //        break;
    //    case "arm":
    //        enc = ctx.mkAnd(enc, ARM.Inconsistent(this, ctx));
    //        break;
    //    default:
    //        System.out.println("Check encodeInconsistent!");
    //        break;
    //    }
    //    return enc;
    //}
    //
    //public Set<XEvent> getEvents() {
    //    Set<XEvent> ret = new HashSet<XEvent>();
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        ret.addAll(iter.next().getEvents());
    //    }
    //    return ret;
    //}
    //
    //public void setMainThread() {
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        t.setMainThread(t.tid);
    //    }
    //}
    //
    //public void setEId() {
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    Integer lastId = 1;
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        lastId = t.setEId(lastId);
    //    }
    //}
    //
    //public void setEId(Integer lastId) {
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        lastId = t.setEId(lastId);
    //    }
    //}
    //
    //public void setTId() {
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    Integer lastId = 1;
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        lastId = t.setTId(lastId);
    //    }
    //}
    //
    //public BoolExpr encodeDF(Context ctx) {
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    MapSSA lastMap = new MapSSA();
    //    BoolExpr enc = ctx.mkTrue();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        Pair<BoolExpr, MapSSA>recResult = t.encodeDF(lastMap, ctx);
    //        enc = ctx.mkAnd(enc, recResult.first());
    //        lastMap = recResult.getSecond();
    //    }
    //    this.lastMap = lastMap;
    //    return enc;
    //}
    //
    //public BoolExpr encodeCF(Context ctx) {
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    BoolExpr enc = ctx.mkTrue();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        enc = ctx.mkAnd(enc, t.encodeCF(ctx));
    //        // Main processes are active
    //        enc = ctx.mkAnd(enc, ctx.mkBoolConst(t.cfVar()));
    //    }
    //    return enc;
    //}
    //
    //public BoolExpr allExecute(Context ctx) {
    //    ListIterator<XProcess> iter = processes.listIterator();
    //    BoolExpr enc = ctx.mkTrue();
    //    while (iter.hasNext()) {
    //        XProcess t = iter.next();
    //        enc = ctx.mkAnd(enc, t.allExecute(ctx));
    //        // Main processes are active
    //        enc = ctx.mkAnd(enc, ctx.mkBoolConst(t.cfVar()));
    //    }
    //    return enc;
    //}
    //
    //public BoolExpr encodeDF_RF(Context ctx) {
    //    BoolExpr enc = ctx.mkTrue();
    //    Set<XEvent> loadEvents = getEvents().stream().filter(e -> e instanceof Load).collect(Collectors.toSet());
    //    for (XEvent r : loadEvents) {
    //        Set<XEvent> storeSameLoc = getEvents().stream().filter(w -> (w instanceof Store || w instanceof Init) && ((MemEvent) w).loc == ((Load) r).loc).collect(Collectors.toSet());
    //        BoolExpr sameValue = ctx.mkTrue();
    //        for (XEvent w : storeSameLoc) {
    //            sameValue = ctx.mkAnd(sameValue, ctx.mkImplies(Utils.edge("rf", w, r, ctx), ctx.mkEq(((MemEvent) w).ssaLoc, ((Load) r).ssaLoc)));
    //        }
    //        enc = ctx.mkAnd(enc, sameValue);
    //    }
    //    return enc;
    //}
    //
    //public List<XProcess> buildRoots() {
    //    return this.processes;
    //}
    //
    //public void setProcesses(List<XProcess> processes) {
    //    this.processes = processes;
    //}

}