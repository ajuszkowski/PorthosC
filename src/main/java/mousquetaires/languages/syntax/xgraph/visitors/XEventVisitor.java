package mousquetaires.languages.syntax.xgraph.visitors;

import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XAssertionEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;


public interface XEventVisitor<T> {

    T visit(XEntryEvent event);

    T visit(XExitEvent event);

    T visit(XUnaryComputationEvent event);

    T visit(XBinaryComputationEvent event);

    T visit(XAssertionEvent event);

    T visit(XInitialWriteEvent event);

    T visit(XRegisterMemoryEvent event);

    T visit(XStoreMemoryEvent event);

    T visit(XLoadMemoryEvent event);

    T visit(XMethodCallEvent event);

    T visit(XJumpEvent event);

    T visit(XNopEvent event);

    T visit(XBarrierEvent event);
}
