package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XAssertionEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Arrays;
import java.util.Collections;


class XMemoryUnitCollector implements XEventVisitor<Iterable<XMemoryUnit>> {

    // TODO!!!! computation events are recursive structures => need to call this recursively! (Although, I think, by construction they are not recursive. Need additional constraints!)

    @Override
    public Iterable<XMemoryUnit> visit(XUnaryComputationEvent event) {
        return Collections.singletonList(event.getOperand());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XBinaryComputationEvent event) {
        return Arrays.asList(event.getFirstOperand(), event.getSecondOperand());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XAssertionEvent event) {
        return event.getAssertion().accept(this);
    }

    //@Override
    //public Iterable<XMemoryUnit> visit(XDeclarationEvent event) {
    //    return Collections.singletonList(event.getUnit());
    //}

    @Override
    public Iterable<XMemoryUnit> visit(XInitialWriteEvent event) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public Iterable<XMemoryUnit> visit(XRegisterMemoryEvent event) {
        return Arrays.asList(event.getSource(), event.getDestination());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XStoreMemoryEvent event) {
        return Arrays.asList(event.getSource(), event.getDestination());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XLoadMemoryEvent event) {
        return Arrays.asList(event.getSource(), event.getDestination());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XEntryEvent event) {
        return Collections.emptyList();
    }

    @Override
    public Iterable<XMemoryUnit> visit(XExitEvent event) {
        return Collections.emptyList();
    }

    @Override
    public Iterable<XMemoryUnit> visit(XJumpEvent event) {
        return Collections.emptyList();
    }

    @Override
    public Iterable<XMemoryUnit> visit(XNopEvent event) {
        return Collections.emptyList();
    }

    @Override
    public Iterable<XMemoryUnit> visit(XBarrierEvent event) {
        return Collections.emptyList();
    }
}
