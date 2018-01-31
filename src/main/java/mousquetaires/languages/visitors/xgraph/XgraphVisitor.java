package mousquetaires.languages.visitors.xgraph;

import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XConditionalJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XUnconditionalJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;


public interface XgraphVisitor<T> {
    T visit(XLocalMemoryUnit node);
    T visit(XSharedMemoryUnit node);
    T visit(XConstant node);

    T visit(XNullaryComputationEvent node);
    T visit(XUnaryOperationEvent node);
    T visit(XBinaryOperationEvent node);

    T visit(XLocalMemoryEvent node);
    T visit(XStoreMemoryEvent node);
    T visit(XLoadMemoryEvent node);

    T visit(XConditionalJumpEvent node);
    T visit(XUnconditionalJumpEvent node);
    T visit(XMethodCallEvent node);

    T visit(XBarrierEvent node);
}
