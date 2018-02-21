package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;

import java.util.Map;


public class XProcess implements XEntity {

    public final XEntryEvent entryEvent;
    public final XExitEvent exitEvent;
    public final String processId;
    /*private*/public final ImmutableList<XEvent> events;
    /*private*/public final Map<XEvent, XEvent> nextEventMap;
    /*private*/public final Map<XComputationEvent, XEvent> thenBranchingJumpsMap; //goto, if(true), while(true)
    /*private*/public final Map<XComputationEvent, XEvent> elseBranchingJumpsMap; //if(false)

    XProcess(XProcessBuilder builder) {
        this.entryEvent = builder.graphBuilder.entryEvent;
        this.exitEvent = builder.graphBuilder.exitEvent;
        this.processId = builder.getProcessId();
        this.events = builder.graphBuilder.buildEvents();
        this.nextEventMap = builder.graphBuilder.buildNextEventMap();
        this.thenBranchingJumpsMap = builder.graphBuilder.buildTrueBranchingJumpsMap();
        this.elseBranchingJumpsMap = builder.graphBuilder.buildFalseBranchingJumpsMap();
    }

    public String getProcessId() {
        return processId;
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }

    //public int condLevel;
    // Main thread where this XEvent, Seq, etc belongs
    //protected Integer mainThread;
    //protected Integer tid;


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
