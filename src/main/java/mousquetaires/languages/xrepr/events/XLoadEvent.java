package mousquetaires.languages.xrepr.events;

public class XLoadEvent extends XMemoryEvent {

    public XLoadEvent(XEventInfo info) {
        super(info);
    }

    //private Register reg;
    //public Integer ssaRegIndex;
    //
    //public Load(Register reg, XMemoryLocation loc) {
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
    //    Set<mousquetaires.execution.events.old.XEvent> set = new HashSet<XEvent>();
    //    set.add(this);
    //    retMap.put(reg, set);
    //    return retMap;
    //}
    //
    //public Load clone() {
    //    Register newReg = reg.clone();
    //    XMemoryLocation newLoc = loc.clone();
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
