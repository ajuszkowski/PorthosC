package mousquetaires.languages.visitors.xgraph;

import mousquetaires.languages.syntax.xgraph.XProgram;
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
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.utils.exceptions.encoding.XEncoderIllegalStateException;


public abstract class XgraphVisitorBase<T> implements XgraphVisitor<T> {

    // abstract entities:
    protected final T visit(XEvent entity) {
        return entity.accept(this);
    }
    protected final T visit(XMemoryEvent entity)  {
        return entity.accept(this);
    }
    protected final T visit(XSharedMemoryEvent entity)  {
        return entity.accept(this);
    }
    protected final T visit(XLocalMemoryEvent entity)  {
        return entity.accept(this);
    }
    protected final T visit(XComputationEvent entity)  {
        return entity.accept(this);
    }
    protected final T visit(XControlFlowEvent entity)  {
        return entity.accept(this);
    }
    protected final T visit(XBarrierEvent entity)  {
        return entity.accept(this);
    }

    @Override
    public T visit(XProgram entity) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XProcess entity) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XRegister entity) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XLocation entity) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XConstant entity) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XNullaryComputationEvent event) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XUnaryOperationEvent event) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XBinaryOperationEvent event) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XRegisterMemoryEvent event) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XStoreMemoryEvent event) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XLoadMemoryEvent event) {
        throw new XEncoderIllegalStateException();
    }

    @Override
    public T visit(XMethodCallEvent event) {
        throw new XEncoderIllegalStateException();
    }

    //@Override
    //public T visit(XBranchingEvent event) {
    //    throw new XEncoderIllegalStateException();
    //}

    @Override
    public T visit(XJumpEvent event) {
        throw new XEncoderIllegalStateException();
    }
}
