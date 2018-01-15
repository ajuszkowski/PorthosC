package mousquetaires.languages.syntax.xrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.syntax.xrepr.events.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XMemoryEvent;


public class XProcess {

    //public final XProcessInfo info;
    public final String name;

    //public int condLevel;
    // Main thread where this XEvent, Seq, etc belongs
    //protected Integer mainThread;
    //protected Integer tid;

    private final ImmutableSet<XMemoryEvent> memoryEvents;
    private final ImmutableSet<XBarrierEvent> barrierEvents;
    private final ImmutableSet<XControlFlowEvent> callEvents;

    public XProcess(String name,
                    ImmutableSet<XMemoryEvent> memoryEvents,
                    ImmutableSet<XBarrierEvent> barrierEvents,
                    ImmutableSet<XControlFlowEvent> callEvents) {
        this.name = name;
        this.memoryEvents = memoryEvents;
        this.barrierEvents = barrierEvents;
        this.callEvents = callEvents;
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
    //public XProcess unroll(int steps) {
    //    System.out.println("Check unroll!");
    //    return this;
    //}

    //public XProcess compile(String target, boolean ctrl, boolean leading) {
    //    System.out.println("Check compile!");
    //    return this;
    //}
    //
    //public XProcess optCompile(boolean ctrl, boolean leading) {
    //    // CHECK!
    //    return compile("", false, true);
    //}
    //
    //public XProcess allCompile() {
    //    System.out.println("Problem all compile!");
    //    return null;
    //}
    //
    //public XProcess clone() {
    //    System.out.println("Problem with clone!");
    //    return new XProcess();
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
    //public Set<XEvent> getEvents() {
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
