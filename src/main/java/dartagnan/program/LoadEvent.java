package dartagnan.program;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.microsoft.z3.*;

import mousquetaires.utils.LastModMap;
import mousquetaires.utils.MapSSA;
import mousquetaires.utils.Pair;
import static mousquetaires.utils.Utils.ssaLoc;
import static mousquetaires.utils.Utils.ssaReg;

public class LoadEvent extends SharedMemEvent {

    private Register reg;
    public Integer ssaRegIndex;

    public LoadEvent(Register reg, Location loc) {
        this.reg = reg;
        this.loc = loc;
        this.condLevel = 0;
    }

    public Register getReg() {
        return reg;
    }

    public String toString() {
        return String.format("%s%s <- %s", String.join("", Collections.nCopies(condLevel, "  ")), reg, loc);
    }

    public LastModMap setLastModMap(LastModMap map) {
        this.lastModMap = map;
        LastModMap retMap = map.clone();
        Set<Event> set = new HashSet<Event>();
        set.add(this);
        retMap.put(reg, set);
        return retMap;
    }

    public LoadEvent clone() {
        Register newReg = reg.clone();
        Location newLoc = loc.clone();
        LoadEvent newLoad = new LoadEvent(newReg, newLoc);
        newLoad.condLevel = condLevel;
        newLoad.setHLId(getHLId());
        return newLoad;
    }

    public Pair<BoolExpr, MapSSA> encodeDF(MapSSA map, Context ctx) throws Z3Exception {
        if(mainThread == null){
            System.out.println(String.format("Check encodeDF for %s", this));
            return null;
        }
        else {
            Expr z3Reg = ssaReg(reg, map.getFresh(reg), ctx);
            Expr z3Loc = ssaLoc(loc, mainThread, map.getFresh(loc), ctx);
            this.ssaLoc = z3Loc;
            this.ssaRegIndex = map.get(reg);
            return new Pair<BoolExpr, MapSSA>(ctx.mkImplies(executes(ctx), ctx.mkEq(z3Reg, z3Loc)), map);
        }
    }
}