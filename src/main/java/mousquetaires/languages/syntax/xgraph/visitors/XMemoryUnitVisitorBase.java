package mousquetaires.languages.syntax.xgraph.visitors;

import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.utils.exceptions.encoding.XEncoderIllegalStateException;


public class XMemoryUnitVisitorBase<T> implements XMemoryUnitVisitor<T> {

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

}
