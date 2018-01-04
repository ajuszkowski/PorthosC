package mousquetaires.execution.events;

public class LoadEvent extends MemoryEvent {

    public LoadEvent(EventInfo info) {
        super(info);
    }

    //private Register reg;
    //public Integer ssaRegIndex;
    //
    //public Load(Register reg, MemoryLocation loc) {
    //    this.reg = reg;
    //    this.loc = loc;
    //    //this.condLevel = 0;
    //}
    //
    //public Register getReg() {
    //    return reg;
    //}
    //
    //public String toString() {
    //    return String.format("%s%s <- %s", String.join("", Collections.nCopies(condLevel, "  ")), reg, loc);
    //}
    //
    //public LastModMap setLastModMap(LastModMap map) {
    //    this.lastModMap = map;
    //    LastModMap retMap = map.clone();
    //    Set<mousquetaires.execution.events.old.Event> set = new HashSet<Event>();
    //    set.add(this);
    //    retMap.put(reg, set);
    //    return retMap;
    //}
    //
    //public Load clone() {
    //    Register newReg = reg.clone();
    //    MemoryLocation newLoc = loc.clone();
    //    Load newLoad = new Load(newReg, newLoc);
    //    newLoad.condLevel = condLevel;
    //    newLoad.setHLId(getHLId());
    //    return newLoad;
    //}
    //
    //public Pair<BoolExpr, MapSSA> encodeDF(MapSSA map, Context ctx) {
    //    if(mainThread == null){
    //        System.out.println(String.format("Check encodeDF for %s", this));
    //        return null;
    //    }
    //    else {
    //        Expr z3Reg = ssaReg(reg, map.getFresh(reg), ctx);
    //        Expr z3Loc = ssaLoc(loc, mainThread, map.getFresh(loc), ctx);
    //        this.ssaLoc = z3Loc;
    //        this.ssaRegIndex = map.get(reg);
    //        return new Pair<BoolExpr, MapSSA>(ctx.mkImplies(executes(ctx), ctx.mkEq(z3Reg, z3Loc)), map);
    //    }
    //}
}
