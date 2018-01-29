package mousquetaires.languages.syntax.ytree.temporaries;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;
import mousquetaires.utils.structures.YReversableList;

import java.util.Iterator;


public class YExpressionReversableList extends YReversableList<YExpression> implements YTempEntity {

    public YExpression[] asArray() {
        return this.toArray(new YExpression[0]);
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new NotSupportedException();
    }

    @Override
    public <S> S accept(YtreeVisitor<S> visitor) {
        throw new NotSupportedException();
    }

    @Override
    public YEntity copy() {
        throw new NotSupportedException();
    }
}
