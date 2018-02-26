package mousquetaires.tests.unit.languages.converters.toxgraph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.languages.syntax.xgraph.processes.XProcessBuilder;
import mousquetaires.utils.patterns.Builder;


public class XProcessTestBuilder extends Builder<XProcess> implements XProcessBuilder {

    private final String processId;
    private final XEntryEvent entryEvent;
    private final XExitEvent exitEvent;
    private final ImmutableList.Builder<XEvent> events;
    private final ImmutableMap.Builder<XEvent, XEvent> nextEventMap;
    private final ImmutableMap.Builder<XComputationEvent, XEvent> thenBranchingJumpsMap;
    private final ImmutableMap.Builder<XComputationEvent, XEvent> elseBranchingJumpsMap;

    public XProcessTestBuilder(String processId) {
        this.processId = processId;
        this.entryEvent = new XEntryEvent(createEventInfo());
        this.exitEvent = new XExitEvent(createEventInfo());
        //ArrayList<XEvent> allEventsList = new ArrayList<>();
        //allEventsList.addAll(nextEventMap.keySet());
        //allEventsList.addAll(thenBranchingJumpsMap.keySet());
        //allEventsList.addAll(elseBranchingJumpsMap.keySet());
        //ImmutableList<XEvent> allEvents = ImmutableList.copyOf(allEventsList);
        //Pair<XEntryEvent, XExitEvent> entryExitPair = XProcessHelper.findEntryAndExitEvents(allEvents);
        this.events = new ImmutableList.Builder<>();
        events.add(this.entryEvent);
        events.add(this.exitEvent);
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
    public ImmutableList<XEvent> buildEvents() {
        return events.build();
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
        XComputationEvent event = new XNullaryComputationEvent(createEventInfo(), first);
        events.add(event);
        return event;
    }

    public XComputationEvent createComputationEvent(XZOperator operator, XLocalMemoryUnit first) {
        XComputationEvent event = new XUnaryOperationEvent(createEventInfo(), operator, first);
        events.add(event);
        return event;
    }

    public XComputationEvent createComputationEvent(XZOperator operator, XLocalMemoryUnit first, XLocalMemoryUnit second) {
        XComputationEvent event = new XBinaryOperationEvent(createEventInfo(), operator, first, second);
        events.add(event);
        return event;
    }

    public XLocalMemoryEvent createAssignmentEvent(XLocalMemoryUnit left, XLocalMemoryUnit right) {
        XLocalMemoryEvent event = new XRegisterMemoryEvent(createEventInfo(), left, right);
        events.add(event);
        return event;
    }

    public XLoadMemoryEvent createAssignmentEvent(XLocalMemoryUnit left, XSharedMemoryUnit right) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(createEventInfo(), left, right);
        events.add(event);
        return event;
    }

    public XStoreMemoryEvent createAssignmentEvent(XSharedMemoryUnit left, XLocalMemoryUnit right) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(createEventInfo(), left, right);
        events.add(event);
        return event;
    }

    //public void processLinearBlock(XEvent... linearEvents) {
    //    XEvent previous = null;
    //    for (XEvent event : linearEvents) {
    //        addEvent(event);
    //        if (previous != null) {
    //            processNextEvent(previous, event);
    //        }
    //        previous = event;
    //    }
    //}


    public void processFirstEvent(XEvent postEntryEvent) {
        nextEventMap.put(entryEvent, postEntryEvent);
    }

    public void processLastEvent(XEvent preExitEvent) {
        nextEventMap.put(preExitEvent, exitEvent);
    }


    public void processNextEvent(XEvent previous, XEvent next) {
        nextEventMap.put(previous, next);
    }

    public void processBranchingEvent(XComputationEvent condition, XEvent firstThen, XEvent firstElse) {
        thenBranchingJumpsMap.put(condition, firstThen);
        elseBranchingJumpsMap.put(condition, firstElse);
    }

    public XJumpEvent createJumpEvent() {
        XJumpEvent event = new XJumpEvent(createEventInfo());
        events.add(event);
        return event;
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(processId);
    }

    //private void addEvent(XEvent event) {
    //    events.add(event);
    //}

}
