package mousquetaires.tests.unit.languages.converters.toxgraph;

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
import mousquetaires.utils.patterns.Builder;


public class XProcessTestBuilder extends Builder<XProcess> {

    private String processId;
    private XEntryEvent entryEvent;
    private XExitEvent exitEvent;

    private ImmutableMap.Builder<XEvent, XEvent> epsilonJumps;
    private ImmutableMap.Builder<XComputationEvent, XEvent> condTrueJumps;
    private ImmutableMap.Builder<XComputationEvent, XEvent> condFalseJumps;

    public XProcessTestBuilder(String processId) {
        this.processId = processId;
        this.entryEvent = new XEntryEvent(createEventInfo());
        //this.exit = new XExitEvent(createEventInfo());
        //ArrayList<XEvent> allEventsList = new ArrayList<>();
        //allEventsList.addAll(epsilonJumps.keySet());
        //allEventsList.addAll(condTrueJumps.keySet());
        //allEventsList.addAll(condFalseJumps.keySet());
        //ImmutableList<XEvent> allEvents = ImmutableList.copyOf(allEventsList);
        //Pair<XEntryEvent, XExitEvent> entryExitPair = XProcessHelper.findEntryAndExitEvents(allEvents);
        //events.add(this.exit);
        this.epsilonJumps = new ImmutableMap.Builder<>();
        this.condTrueJumps = new ImmutableMap.Builder<>();
        this.condFalseJumps = new ImmutableMap.Builder<>();
    }

    @Override
    public XProcess build() {
        return new XProcess(processId, entryEvent, exitEvent,
                epsilonJumps.build(), condTrueJumps.build(), condFalseJumps.build());
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
        epsilonJumps.put(entryEvent, postEntryEvent);
    }

    public void processLastEvent(XEvent preExitEvent) {
        exitEvent = new XExitEvent(createEventInfo());
        epsilonJumps.put(preExitEvent, exitEvent);
    }

    public void processNextEvent(XEvent previous, XEvent next) {
        epsilonJumps.put(previous, next);
    }

    public void processBranchingEvent(XComputationEvent condition, XEvent firstThen, XEvent firstElse) {
        condTrueJumps.put(condition, firstThen);
        condFalseJumps.put(condition, firstElse);
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(processId);
    }

}
