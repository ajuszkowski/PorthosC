package mousquetaires.languages.syntax.xgraph.visitors;

import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;


public interface XEventVisitor<T> {

    T visit(XNullaryComputationEvent entity);
    T visit(XUnaryOperationEvent entity);
    T visit(XBinaryOperationEvent entity);

    T visit(XRegisterMemoryEvent entity);
    T visit(XStoreMemoryEvent entity);
    T visit(XLoadMemoryEvent entity);

    //T visit(XBranchingEvent entity);
    T visit(XJumpEvent entity);
    T visit(XMethodCallEvent entity);

    //todo: barriers impl
}
