package mousquetaires.tests.unit.languages.converters.toxgraph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.languages.syntax.xgraph.processes.XProcessBuilder;
import mousquetaires.utils.patterns.Builder;


public class XProcessTestBuilder extends Builder<XProcess> implements XProcessBuilder {

    private String processId;
    private XEntryEvent entryEvent;
    private XExitEvent exitEvent;

    private ImmutableMap.Builder<XEvent, XEvent> nextEventMap;
    private ImmutableMap.Builder<XComputationEvent, XEvent> thenBranchingJumpsMap;
    private ImmutableMap.Builder<XComputationEvent, XEvent> elseBranchingJumpsMap;

    public XProcessTestBuilder(String processId) {
        this.processId = processId;
        this.entryEvent = new XEntryEvent(createEventInfo());
        //this.exitEvent = new XExitEvent(createEventInfo());
        //ArrayList<XEvent> allEventsList = new ArrayList<>();
        //allEventsList.addAll(nextEventMap.keySet());
        //allEventsList.addAll(thenBranchingJumpsMap.keySet());
        //allEventsList.addAll(elseBranchingJumpsMap.keySet());
        //ImmutableList<XEvent> allEvents = ImmutableList.copyOf(allEventsList);
        //Pair<XEntryEvent, XExitEvent> entryExitPair = XProcessHelper.findEntryAndExitEvents(allEvents);
        //events.add(this.exitEvent);
        this.nextEventMap = new ImmutableMap.Builder<>();
        this.thenBranchingJumpsMap = new ImmutableMap.Builder<>();
        this.elseBranchingJumpsMap = new ImmutableMap.Builder<>();
    }

    @Override
    public String buildProcessId() {
        return processId;
    }

    @Override
    public XEntryEvent buildEntryEvent() {
        return entryEvent;
    }

    @Override
    public XExitEvent buildExitEvent() {
        return exitEvent;
    }

    @Override
    public ImmutableMap<XEvent, XEvent> buildNextEventMap() {
        return nextEventMap.build();
    }

    @Override
    public ImmutableMap<XComputationEvent, XEvent> buildThenBranchingJumpsMap() {
        return thenBranchingJumpsMap.build();
    }

    @Override
    public ImmutableMap<XComputationEvent, XEvent> buildElseBranchingJumpsMap() {
        return elseBranchingJumpsMap.build();
    }

    @Override
    public XProcess build() {
        return new XProcess(this);
    }


    //private static Pair<XEntryEvent, XExitEvent> findEntryAndExitEvents(ImmutableList<XEvent> events) {
    //    XEntryEvent entry = null;
    //    XExitEvent exit = null;
    //    boolean firstWasFound = false;
    //    for (XEvent event : events) {
    //        if (entry == null && event instanceof XEntryEvent) {
    //            entry = (XEntryEvent) event;
    //            if (firstWasFound) {
    //                break;
    //            }
    //            firstWasFound = true;
    //        }
    //        else if (exit == null && event instanceof XExitEvent) {
    //            exit = (XExitEvent) event;
    //            if (firstWasFound) {
    //                break;
    //            }
    //            firstWasFound = true;
    //        }
    //    }
    //    if (entry == null) {
    //        throw new RuntimeException("Entry point was not found");
    //    }
    //    if (exit == null) {
    //        throw new RuntimeException("Exit point was not found");
    //    }
    //    return new Pair<>(entry, exit);
    //}

    public XComputationEvent createComputationEvent(XLocalMemoryUnit first) {
        return new XNullaryComputationEvent(createEventInfo(), first);
    }

    public XComputationEvent createComputationEvent(XZOperator operator, XLocalMemoryUnit first) {
        return new XUnaryOperationEvent(createEventInfo(), operator, first);
    }

    public XComputationEvent createComputationEvent(XZOperator operator, XLocalMemoryUnit first, XLocalMemoryUnit second) {
        return new XBinaryOperationEvent(createEventInfo(), operator, first, second);
    }

    public XLocalMemoryEvent createAssignmentEvent(XLocalMemoryUnit left, XLocalMemoryUnit right) {
        return new XRegisterMemoryEvent(createEventInfo(), left, right);
    }

    public XLoadMemoryEvent createAssignmentEvent(XLocalMemoryUnit left, XSharedMemoryUnit right) {
        return new XLoadMemoryEvent(createEventInfo(), left, right);
    }

    public XStoreMemoryEvent createAssignmentEvent(XSharedMemoryUnit left, XLocalMemoryUnit right) {
        return new XStoreMemoryEvent(createEventInfo(), left, right);
    }

    public void processFirstEvent(XEvent postEntryEvent) {
        nextEventMap.put(entryEvent, postEntryEvent);
    }

    public void processLastEvent(XEvent preExitEvent) {
        exitEvent = new XExitEvent(createEventInfo());
        nextEventMap.put(preExitEvent, exitEvent);
    }

    public void processNextEvent(XEvent previous, XEvent next) {
        nextEventMap.put(previous, next);
    }

    public void processBranchingEvent(XComputationEvent condition, XEvent firstThen, XEvent firstElse) {
        thenBranchingJumpsMap.put(condition, firstThen);
        elseBranchingJumpsMap.put(condition, firstElse);
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(processId);
    }

}
