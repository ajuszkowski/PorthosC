package porthosc.tests.unit.languages.converters.toxgraph;

import porthosc.languages.syntax.xgraph.events.XEvent;
import porthosc.languages.syntax.xgraph.events.XEventInfo;
import porthosc.languages.syntax.xgraph.events.computation.*;
import porthosc.languages.syntax.xgraph.events.fake.XEntryEvent;
import porthosc.languages.syntax.xgraph.events.fake.XExitEvent;
import porthosc.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import porthosc.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import porthosc.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import porthosc.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import porthosc.languages.syntax.xgraph.memories.XLocalLvalueMemoryUnit;
import porthosc.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import porthosc.languages.syntax.xgraph.memories.XSharedLvalueMemoryUnit;
import porthosc.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import porthosc.languages.syntax.xgraph.process.XProcessId;
import porthosc.languages.syntax.xgraph.process.XCyclicProcess;
import porthosc.languages.syntax.xgraph.process.XCyclicProcessBuilder;
import porthosc.utils.patterns.BuilderBase;


// TODO:!!!!! inherit it from TestFlowGraphBuilderBase!
public class XFlowGraphTestBuilder extends BuilderBase<XCyclicProcess> {

    private final XCyclicProcessBuilder builder;

    public XFlowGraphTestBuilder(XProcessId processId) {
        builder = new XCyclicProcessBuilder(processId);
        builder.setSource(new XEntryEvent(createEventInfo()));
        builder.setSink(new XExitEvent(createEventInfo()));
    }

    @Override
    public XCyclicProcess build() {
        return builder.build();
    }

    public XComputationEvent createComputationEvent(XLocalMemoryUnit first) {
        return new XUnaryComputationEvent(createEventInfo(), XUnaryOperator.NoOperation, first);
    }

    public XComputationEvent createComputationEvent(XBinaryOperator operator, XLocalMemoryUnit first, XLocalMemoryUnit second) {
        return new XBinaryComputationEvent(createEventInfo(), operator, first, second);
    }

    public XLocalMemoryEvent createAssignmentEvent(XLocalLvalueMemoryUnit left, XLocalMemoryUnit right) {
        return new XRegisterMemoryEvent(createEventInfo(), left, right);
    }

    public XLoadMemoryEvent createAssignmentEvent(XLocalLvalueMemoryUnit left, XSharedMemoryUnit right) {
        return new XLoadMemoryEvent(createEventInfo(), left, right);
    }

    public XStoreMemoryEvent createAssignmentEvent(XSharedLvalueMemoryUnit left, XLocalMemoryUnit right) {
        return new XStoreMemoryEvent(createEventInfo(), left, right);
    }

    public void processFirstEvent(XEvent postEntryEvent) {
        builder.addEdge(true, builder.getSource(), postEntryEvent);
    }

    public void processLastEvents(XEvent... preExitEvents) {
        for (XEvent event : preExitEvents) {
            builder.addEdge(true, event, builder.getSink());
        }
    }

    public void processNextEvent(XEvent first, XEvent second, XEvent... others) {
        builder.addEdge(true, first, second);
        XEvent previous = second, next;
        for (XEvent other : others) {
            next = other;
            builder.addEdge(true, previous, next);
            previous = next;
        }
    }

    public void processBranchingEvent(XEvent condition, XEvent firstThen, XEvent firstElse) {
        builder.addEdge(true, condition, firstThen);
        builder.addEdge(false, condition, firstElse);
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(builder.getProcessId());
    }
}
