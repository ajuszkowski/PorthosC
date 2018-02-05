package mousquetaires.languages.visitors.xgraph;

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


public interface XgraphVisitor<T> {
    // abstract nodes:
    //T visit(XEvent node);

    //T visit(XMemoryEvent node);
    //
    //T visit(XSharedMemoryEvent node);
    //T visit(XLocalMemoryEvent node);
    //T visit(XComputationEvent node);
    //T visit(XControlFlowEvent node);
    //T visit(XBarrierEvent node);

    //concrete nodes:
    T visit(XRegister node);
    T visit(XLocation node);
    T visit(XConstant node);

    T visit(XNullaryComputationEvent node);
    T visit(XUnaryOperationEvent node);
    T visit(XBinaryOperationEvent node);

    T visit(XRegisterMemoryEvent node);
    T visit(XStoreMemoryEvent node);
    T visit(XLoadMemoryEvent node);

    T visit(XJumpEvent node);
    T visit(XMethodCallEvent node);

    //todo: barriers impl
}
