package mousquetaires.tests.unit.languages.converters.toxgraph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraphBuilder;
import mousquetaires.utils.exceptions.NotSupportedException;


public class XFlowGraphTestBuilder extends XFlowGraphBuilder {

    // TODO: use integer-graph TestFlowGraph
    public XFlowGraphTestBuilder(String processId) {
        super(processId);
        super.setSource(new XEntryEvent(createEventInfo()));
        super.setSink(new XExitEvent(createEventInfo()));
    }

    @Override
    public void setSource(XEvent source) {
        throw new NotSupportedException();
    }

    @Override
    public void setSink(XEvent sink) {
        throw new NotSupportedException();
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
        addEdge(getSource(), postEntryEvent);
    }

    public void processLastEvents(XEvent... preExitEvents) {
        for (XEvent event : preExitEvents) {
            addEdge(event, getSink());
        }
    }

    public void processNextEvent(XEvent first, XEvent second, XEvent... others) {
        addEdge(first, second);
        XEvent previous = second, next;
        for (XEvent other : others) {
            next = other;
            addEdge(previous, next);
            previous = next;
        }
    }

    public void processBranchingEvent(XEvent condition, XEvent firstThen, XEvent firstElse) {
        addEdge(condition, firstThen);
        addAlternativeEdge(condition, firstElse);
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(getProcessId());
    }
}
