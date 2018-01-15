package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.converters.toxrepr.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;


/** Load event from shared memoryevents ({@link XSharedMemoryUnit})
 * to local storage (registry, {@link XLocalMemoryUnit}) */
public class XLoadMemoryEvent extends XSharedMemoryEvent {

    public final XSharedMemoryUnit source;
    public final XLocalMemoryUnit destination;
    //public final XMemoryOrder memoryOrder;

    public XLoadMemoryEvent(XEventInfo info, XLocalMemoryUnit destination, XSharedMemoryUnit source/*, XMemoryOrder memoryOrder*/) {
        super(info);
        if (destination instanceof XValue) {
            throw new IllegalArgumentException("Memory event with assignment to " + XValue.class.getName()
                    + " is not allowed");
        }
        this.destination = destination;
        this.source = source;
        //this.memoryOrder = memoryOrder;
    }

    @Override
    public String toString() {
        return destination + "<- load(" + source /*+ ", " + memoryOrder*/ + ")";
    }

    //private Register reg;
    //public Integer ssaRegIndex;
    //
    //public Load(Register reg, XMemoryUnit loc) {
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
    //    XMemoryUnit newLoc = loc.clone();
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
