package mousquetaires.languages.syntax.ytree.specific;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public class YAssertionStatement extends YPostludeStatement {

    private final YExpression expression;

    public YAssertionStatement(YExpression expression) {
        this.expression = expression;
    }

    public YExpression getExpression() {
        return expression;
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(expression);
    }

    @Override
    public YEntity copy() {
        return new YAssertionStatement(getExpression());
    }

    @Override
    public String toString() {
        return "exists( " + expression + " )";
    }
}
