package mousquetaires.languages.eventrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.eventrepr.events.BarrierEvent;
import mousquetaires.languages.eventrepr.events.CallEvent;
import mousquetaires.languages.eventrepr.events.MemoryEvent;


public class Processus {

    //public final ProcessusInfo info;
    public final String name;

    //public int condLevel;
    // Main thread where this Event, Seq, etc belongs
    //protected Integer mainThread;
    //protected Integer tid;

    private final ImmutableSet<MemoryEvent> memoryEvents;
    private final ImmutableSet<BarrierEvent> barrierEvents;
    private final ImmutableSet<CallEvent> callEvents;

    public Processus(ProcessusBuilder builder) {
        this.name = builder.getName();
        this.memoryEvents = builder.getMemoryEvents();
        this.barrierEvents = builder.getBarrierEvents();
        this.callEvents = builder.getCallEvents();
    }

    //public int getCondLevel() {
    //    return condLevel;
    //}

    //public void setCondLevel(int condLevel) {
    //    IntStream.range(0, condLevel).forEachOrdered(n -> {
    //        incCondLevel();
    //    });
    //}
    //
    //public void incCondLevel() {
    //    condLevel++;
    //}
    //
    //public void decCondLevel() {
    //    condLevel--;
    //}
    //
    //public Processus unroll(int steps) {
    //    System.out.println("Check unroll!");
    //    return this;
    //}

    //public Processus compile(String target, boolean ctrl, boolean leading) {
    //    System.out.println("Check compile!");
    //    return this;
    //}
    //
    //public Processus optCompile(boolean ctrl, boolean leading) {
    //    // CHECK!
    //    return compile("", false, true);
    //}
    //
    //public Processus allCompile() {
    //    System.out.println("Problem all compile!");
    //    return null;
    //}
    //
    //public Processus clone() {
    //    System.out.println("Problem with clone!");
    //    return new Processus();
    //}
    //
    //public Integer getMainThread() {
    //    return mainThread;
    //}

    //public BoolExpr encodeCF(Context ctx) {
    //    System.out.println("Check encodeCF!");
    //    // TODO Auto-generated method stub
    //    return null;
    //}
    //
    //public Pair<BoolExpr, MapSSA> encodeDF(MapSSA map, Context ctx) {
    //    // TODO Auto-generated method stub
    //    return null;
    //}
    //
    //public void setMainThread(Integer t) {
    //    // TODO Auto-generated method stub
    //}
    //
    //public Integer setEId(Integer i) {
    //    // TODO Auto-generated method stub
    //    return null;
    //}
    //
    //public Set<Event> getEvents() {
    //    // TODO Auto-generated method stub
    //    return null;
    //}
    //
    //public Integer setTId(Integer i) {
    //    // TODO Auto-generated method stub
    //    return null;
    //}
    //
    //public String cfVar() {
    //    return "CF" + hashCode();
    //}
    //
    //public void setCondRegs(Set<Register> setRegs) {
    //    // TODO Auto-generated method stub
    //
    //}
    //
    //public LastModMap setLastModMap(LastModMap newMap) {
    //    // TODO Auto-generated method stub
    //    return null;
    //}
    //
    //public Integer getTId() {
    //    return this.tid;
    //}
    //
    //public BoolExpr allExecute(Context ctx) {
    //    // TODO Auto-generated method stub
    //    System.out.println("Check allExecute!");
    //    return null;
    //}
}
