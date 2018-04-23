package mousquetaires.languages.syntax.xgraph.visitors;

import mousquetaires.languages.syntax.xgraph.events.computation.XAssertionEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;


public class XMemoryUnitVisitorBase<T> implements XMemoryUnitVisitor<T> {

    @Override
    public T visit(XRegister entity) {
        throw new XVisitorIllegalStateException();
    }

    @Override
    public T visit(XLocation entity) {
        throw new XVisitorIllegalStateException();
    }

    @Override
    public T visit(XConstant entity) {
        throw new XVisitorIllegalStateException();
    }

    @Override
    public T visit(XUnaryComputationEvent entity) {
        throw new XVisitorIllegalStateException();
    }

    @Override
    public T visit(XBinaryComputationEvent entity) {
        throw new XVisitorIllegalStateException();
    }

    @Override
    public T visit(XAssertionEvent entity) {
        throw new XVisitorIllegalStateException();
    }
}
