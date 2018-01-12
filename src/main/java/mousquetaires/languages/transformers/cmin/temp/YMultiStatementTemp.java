package mousquetaires.languages.transformers.cmin.temp;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.statements.YStatement;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Iterator;


public class YMultiStatementTemp extends YStatement implements YTempEntity {

    public final ImmutableList<YStatement> statements;

    YMultiStatementTemp(ImmutableList<YStatement> statements) {
        super(null);
        this.statements = statements;
    }

    @Override
    public YStatement withLabel(String newLabel) {
        throw new NotSupportedException();
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(statements);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        //todo
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return "YMultiStatementTemp{" + statements + '}';
    }
}
