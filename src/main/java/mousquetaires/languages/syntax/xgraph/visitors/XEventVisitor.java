package mousquetaires.languages.syntax.xgraph.visitors;

import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;


public interface XEventVisitor<T> {

    //T visit(XEventRef event);

    T visit(XEntryEvent event);
    T visit(XExitEvent event);

    T visit(XNullaryComputationEvent event);
    T visit(XUnaryComputationEvent event);
    T visit(XBinaryComputationEvent event);

    T visit(XRegisterMemoryEvent event);
    T visit(XStoreMemoryEvent event);
    T visit(XLoadMemoryEvent event);

    //T visit(XBranchingEvent event);
    T visit(XJumpEvent event);
    T visit(XMethodCallEvent event);

    //todo: barriers impl
}
