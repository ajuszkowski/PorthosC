package old.dartagnan.program;

import java.util.HashSet;
import java.util.Set;

import com.microsoft.z3.*;

import old.dartagnan.expression.AExpr;
import porthosc.utils.LastModMap;
import porthosc.utils.MapSSA;
import porthosc.utils.Pair;

public class Event extends Thread {

    protected Integer eid;
    private Integer hlId;

    protected Set<Register> condReg;
    protected LastModMap lastModMap;

    public Event() {}

    // topologically (?) ordered id = po relation
    // make sure eid is unqiue cross multiple programs
    public Integer getEId() {
        return eid;
    }

    public void setHLId(Integer id) {
        this.hlId = id;
    }

    // *a = *b;

    // reg_a = load(a) //hlid same
    // store(reg_a, b) //hlid same

    // high-level id
    public Integer  getHLId() {
        return hlId;
    }

    public LastModMap getLastModMap() {
        assert(lastModMap.size() != 0);
        return lastModMap;
    }

    public LastModMap setLastModMap(LastModMap map) {
        this.lastModMap =  map;
        return map;
    }

    public void incCondLevel() {
        condLevel++;
    }

    public Thread unroll(int steps) {
        return this;
    }

    public Thread compile(String target, boolean ctrl, boolean leading) {
        setHLId(hashCode());
        return this;
    }

    public Thread allCompile() {
        OptSync os = new OptSync();
        os.condLevel = condLevel;
        OptLwsync olws = new OptLwsync();
        olws.condLevel = condLevel;
        return new Seq(os, new Seq(olws, this));
    }

    public String repr() {
        return String.format("E%s", eid);
    }

    public BoolExpr executes(Context ctx) throws Z3Exception {
        return ctx.mkBoolConst(String.format("ex(%s)", repr()));
    }

    public Pair<BoolExpr, MapSSA> encodeDF(MapSSA map, Context ctx) throws Z3Exception {
        return new Pair<BoolExpr, MapSSA>(ctx.mkTrue(), map);
    }

    public void setMainThread(Integer t) {
        this.mainThread = t;
    }

    public Integer setEId(Integer i) {
        this.eid = i;
        return i+1;
    }

    public Integer setTId(Integer i) {
        this.tid = i;
        return i+1;
    }

    public Set<Event> getEvents() {
        Set<Event> ret = new HashSet<Event>();
        ret.add(this);
        return ret;
    }

    public BoolExpr encodeCF(Context ctx) throws Z3Exception {
        return ctx.mkEq(ctx.mkBoolConst(cfVar()), executes(ctx));
    }

    public BoolExpr allExecute(Context ctx) throws Z3Exception {
        return ctx.mkEq(ctx.mkBoolConst(cfVar()), executes(ctx));
    }

    public Set<Register> getCondRegs() {
        if(condReg == null) {
            System.out.println(String.format("Check getCondRegs for %s: null pointer!", repr()));
        }
        return condReg;
    }

    public void setCondRegs(Set<Register> regSet) {
        condReg = regSet;
    }

    public Register getReg() {
        // This should be never executes; should match a more concrete class
        System.out.println(String.format("Check getReg for %s", this));
        return null;
    }


    // the ssa-index for current event
    public Integer getSsaRegIndex() {
        if(this instanceof LocalEvent) {
            return ((LocalEvent)this).ssaRegIndex;
        }
        if(this instanceof LoadEvent) {
            return ((LoadEvent)this).ssaRegIndex;
        }
        System.out.println(String.format("Check getSSAReg for %s", this));
        return null;
    }

    public Location getLoc() {
        // This should be never executes; should match a more concrete class
        System.out.println(String.format("Check getLoc for %s", this));
        return null;
    }

    public AExpr getExpr() {
        // This should be never executes; should match a more concrete class
        System.out.println(String.format("Check getExpr for %s", this));
        return null;
    }
}