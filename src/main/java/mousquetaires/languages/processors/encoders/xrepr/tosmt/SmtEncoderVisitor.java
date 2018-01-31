package mousquetaires.languages.processors.encoders.xrepr.tosmt;

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
import mousquetaires.languages.visitors.xrepr.XreprVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


public class SmtEncoderVisitor implements XreprVisitor<String> {

    @Override
    public String visit(XLocalMemoryUnit node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XSharedMemoryUnit node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XConstant node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XNullaryComputationEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XUnaryOperationEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XBinaryOperationEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XLocalMemoryEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XStoreMemoryEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XLoadMemoryEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XConditionalJumpEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XUnconditionalJumpEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XMethodCallEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XBarrierEvent node) {
        throw new NotImplementedException();
    }
}
