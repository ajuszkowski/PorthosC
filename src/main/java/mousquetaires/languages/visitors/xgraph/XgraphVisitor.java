package mousquetaires.languages.visitors.xgraph;

import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;


public interface XgraphVisitor<T> {

    T visit(XProgram entity);
    T visit(XProcess entity);

    T visit(XRegister entity);
    T visit(XLocation entity);
    T visit(XConstant entity);

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
