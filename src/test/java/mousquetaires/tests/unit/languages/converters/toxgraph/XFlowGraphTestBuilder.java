package mousquetaires.tests.unit.languages.converters.toxgraph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.zformula.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessBuilder;
import mousquetaires.utils.patterns.BuilderBase;


// TODO:!!!!! inherit it from TestFlowGraphBuilderBase!
public class XFlowGraphTestBuilder extends BuilderBase<XProcess> {

    private final XProcessBuilder builder;

    public XFlowGraphTestBuilder(String processId) {
        builder = new XProcessBuilder(processId);
        builder.setSource(new XEntryEvent(createEventInfo()));
        builder.setSink(new XExitEvent(createEventInfo()));
    }

    @Override
    public XProcess build() {
        return builder.build();
    }

    public XComputationEvent createComputationEvent(XLocalMemoryUnit first) {
        return new XNullaryComputationEvent(createEventInfo(), first);
    }

    public XComputationEvent createComputationEvent(XZOperator operator, XLocalMemoryUnit first) {
        return new XUnaryComputationEvent(createEventInfo(), operator, first);
    }

    public XComputationEvent createComputationEvent(XZOperator operator, XLocalMemoryUnit first, XLocalMemoryUnit second) {
        return new XBinaryComputationEvent(createEventInfo(), operator, first, second);
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
