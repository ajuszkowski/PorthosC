//package mousquetaires.languages.visitors.xgraph;
//
//import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperationEvent;
//import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
//import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperationEvent;
//import mousquetaires.languages.syntax.xgraph.events.controlflow.XBranchingEvent;
//import mousquetaires.languages.syntax.xgraph.events.controlflow.XMethodCallEvent;
//import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
//import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
//import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
//import mousquetaires.languages.syntax.xgraph.memories.XConstant;
//import mousquetaires.languages.syntax.xgraph.memories.XLocation;
//import mousquetaires.languages.syntax.xgraph.memories.XRegister;
//import mousquetaires.utils.exceptions.NotSupportedException;
//
//
//public abstract class XgraphAbstractVisitorBase<T> implements XgraphVisitor<T> {
//
//    @Override
//    public T visit(XRegister node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XLocation node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XConstant node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XNullaryComputationEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XUnaryOperationEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XBinaryOperationEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XRegisterMemoryEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XStoreMemoryEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XLoadMemoryEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XBranchingEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XMethodCallEvent node) {
//        throw new NotSupportedException();
//    }
//}
