package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Iterator;


public class YFakeStatement extends YStatement {

    private YFakeStatement(String label) {
        super(label);
    }

    @Override
    public YStatement withLabel(String newLabel) {
        return new YFakeStatement(newLabel);
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new NotImplementedException(); // not implemented? not supp?
    }

    @Override
    public YEntity copy() {
        return new YFakeStatement(getLabel());
    }

    public static YFakeStatement create() {
        return new YFakeStatement(newLabel());
    }
}
