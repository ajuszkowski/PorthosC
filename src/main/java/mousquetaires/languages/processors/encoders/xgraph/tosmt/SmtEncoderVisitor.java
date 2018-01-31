package mousquetaires.languages.processors.encoders.xgraph.tosmt;

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
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


public class SmtEncoderVisitor implements XgraphVisitor<String> {

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
