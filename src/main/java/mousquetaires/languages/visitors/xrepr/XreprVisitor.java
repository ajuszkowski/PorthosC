package mousquetaires.languages.visitors.xrepr;

import mousquetaires.languages.syntax.xrepr.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XConditionalJumpEvent;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XUnconditionalJumpEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xrepr.memories.XConstant;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;


public interface XreprVisitor<T> {
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
