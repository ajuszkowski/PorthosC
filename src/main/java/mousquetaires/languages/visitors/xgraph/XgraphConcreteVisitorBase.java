//package mousquetaires.languages.visitors.xgraph;
//
//import mousquetaires.languages.syntax.xgraph.events.XEvent;
//import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
//import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
//import mousquetaires.languages.syntax.xgraph.events.controlflow.XControlFlowEvent;
//import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
//import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
//import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
//import mousquetaires.utils.exceptions.NotSupportedException;
//
//
//public abstract class XgraphConcreteVisitorBase<T> implements XgraphVisitor<T> {
//
//    @Override
//    public T visit(XEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XMemoryEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XSharedMemoryEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XLocalMemoryEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XComputationEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XControlFlowEvent node) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public T visit(XBarrierEvent node) {
//        throw new NotSupportedException();
//    }
//}
