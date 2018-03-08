package mousquetaires.languages.syntax.xgraph.visitors;

import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;


public interface XEventVisitor<T> {

    T visit(XNullaryComputationEvent entity);
    T visit(XUnaryComputationEvent entity);
    T visit(XBinaryComputationEvent entity);

    T visit(XRegisterMemoryEvent entity);
    T visit(XStoreMemoryEvent entity);
    T visit(XLoadMemoryEvent entity);

    //T visit(XBranchingEvent entity);
    T visit(XJumpEvent entity);
    T visit(XMethodCallEvent entity);

    //todo: barriers impl
}
