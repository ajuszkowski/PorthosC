package mousquetaires.languages.processors.encoders.xgraph.tosmt;

import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XBranchingEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


public class SmtEncoderVisitor implements XgraphVisitor<String> {

    @Override
    public String visit(XRegister node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XLocation node) {
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
    public String visit(XRegisterMemoryEvent node) {
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
    public String visit(XBranchingEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XMethodCallEvent node) {
        throw new NotImplementedException();
    }

    //@Override
    //public String visit(XBarrierEvent node) {
    //    throw new NotImplementedException();
    //}
}
