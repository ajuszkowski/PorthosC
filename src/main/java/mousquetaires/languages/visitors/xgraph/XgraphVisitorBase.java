package mousquetaires.languages.visitors.xgraph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XControlFlowEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.utils.exceptions.NotImplementedException;


public abstract class XgraphVisitorBase<T> implements XgraphVisitor<T> {

    protected T visit(XEvent node) {
        return node.accept(this);
    }
    protected T visit(XMemoryEvent node)  {
        return node.accept(this);
    }
    protected T visit(XSharedMemoryEvent node)  {
        return node.accept(this);
    }
    protected T visit(XLocalMemoryEvent node)  {
        return node.accept(this);
    }
    protected T visit(XComputationEvent node)  {
        return node.accept(this);
    }
    protected T visit(XControlFlowEvent node)  {
        return node.accept(this);
    }
    protected T visit(XBarrierEvent node)  {
        return node.accept(this);
    }

    @Override
    public T visit(XRegister node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XLocation node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XConstant node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XNullaryComputationEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XUnaryOperationEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XBinaryOperationEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XRegisterMemoryEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XStoreMemoryEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XLoadMemoryEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public T visit(XMethodCallEvent node) {
        throw new NotImplementedException();
    }

    //@Override
    //public T visit(XBranchingEvent node) {
    //    throw new NotImplementedException();
    //}

    @Override
    public T visit(XJumpEvent node) {
        throw new NotImplementedException();
    }
}
