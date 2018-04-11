package dartagnan.program;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.microsoft.z3.*;

import mousquetaires.utils.LastModMap;
import mousquetaires.utils.MapSSA;
import mousquetaires.utils.Pair;

import static mousquetaires.utils.Utils.ssaReg;
import static mousquetaires.utils.Utils.ssaLoc;

public class StoreEvent extends SharedMemEvent {

    private Register reg;

    public StoreEvent(Location loc, Register reg) {
        this.reg = reg;
        this.loc = loc;
        this.condLevel = 0;
    }

    public Register getReg() {
        return reg;
    }

    public String toString() {
        return String.format("%s%s := %s", String.join("", Collections.nCopies(condLevel, "  ")), loc, reg);
    }

    public LastModMap setLastModMap(LastModMap map) {
        this.lastModMap = map;
        LastModMap retMap = map.clone();
        Set<Event> set = new HashSet<Event>();
        set.add(this);
        retMap.put(loc, set);
        return retMap;
    }

    public StoreEvent clone() {
        Register newReg = reg.clone();
        Location newLoc = loc.clone();
        StoreEvent newStore = new StoreEvent(newLoc, newReg);
        newStore.condLevel = condLevel;
        newStore.setHLId(getHLId());
        return newStore;
    }

    public Pair<BoolExpr, MapSSA> encodeDF(MapSSA map, Context ctx) throws Z3Exception {
        if(mainThread == null){
            System.out.println(String.format("Check encodeDF for %s", this));
            return null;
        }
        else {
            Expr z3Loc = ssaLoc(loc, mainThread, map.getFresh(loc), ctx);
            this.ssaLoc = z3Loc;
            Expr z3Reg = ssaReg(reg, map.get(reg), ctx);
            return new Pair<BoolExpr, MapSSA>(ctx.mkImplies(executes(ctx), ctx.mkEq(z3Loc, z3Reg)), map);
        }
    }
}