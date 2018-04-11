package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XInitialWriteEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Arrays;
import java.util.Collections;


public class XLocalMemoryUnitCollector implements XEventVisitor<Iterable<XLocalMemoryUnit>> {

    @Override
    public Iterable<XLocalMemoryUnit> visit(XUnaryComputationEvent event) {
        return Collections.singletonList(event.getOperand());
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XBinaryComputationEvent event) {
        return Arrays.asList(event.getFirstOperand(), event.getSecondOperand());
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XInitialWriteEvent event) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XRegisterMemoryEvent event) {
        return Arrays.asList(event.getSource(), event.getDestination());
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XStoreMemoryEvent event) {
        return Collections.singletonList(event.getSource());
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XLoadMemoryEvent event) {
        return Collections.singletonList(event.getDestination());
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XEntryEvent event) {
        return Collections.emptyList();
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XExitEvent event) {
        return Collections.emptyList();
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XJumpEvent event) {
        return Collections.emptyList();
    }

    @Override
    public Iterable<XLocalMemoryUnit> visit(XNopEvent event) {
        return Collections.emptyList();
    }

    public Iterable<XLocalMemoryUnit> visit(XBarrierEvent event) {
        return Collections.emptyList();
    }
}
