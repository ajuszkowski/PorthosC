package dartagnan.program;

import com.microsoft.z3.*;

import porthosc.utils.LastModMap;
import porthosc.utils.MapSSA;
import porthosc.utils.Pair;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static porthosc.utils.Utils.ssaLoc;

public class InitEvent extends SharedMemEvent {

    public InitEvent(Location loc) {
        setHLId(hashCode());
        this.loc = loc;
        this.condLevel = 0;
    }

    public String toString() {
        return String.format("%s%s := 0", String.join("", Collections.nCopies(condLevel, "  ")), loc);
    }

    public InitEvent clone() {
        Location newLoc = loc.clone();
        InitEvent newInit = new InitEvent(newLoc);
        newInit.condLevel = condLevel;
        newInit.setHLId(getHLId());
        return newInit;
    }

    public LastModMap setLastModMap(LastModMap map) {
        this.lastModMap = map;
        LastModMap retMap = map.clone();
        Set<Event> set = new HashSet<Event>();
        set.add(this);
        retMap.put(loc, set);
        return retMap;
    }

    public Pair<BoolExpr, MapSSA> encodeDF(MapSSA map, Context ctx) throws Z3Exception {
        if(mainThread == null){
            System.out.println(String.format("Check encodeDF for %s", this));
            return null;
        }
        else {
            Expr z3Loc = ssaLoc(loc, mainThread, map.getFresh(loc), ctx);
            this.ssaLoc = z3Loc;
            return new Pair<BoolExpr, MapSSA>(ctx.mkEq(z3Loc, ctx.mkInt(0)), map);
        }
    }
}