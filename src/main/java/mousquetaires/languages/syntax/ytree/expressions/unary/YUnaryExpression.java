package mousquetaires.languages.syntax.ytree.expressions.unary;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public abstract class YUnaryExpression extends YMultiExpression {
    public interface Kind {
        YUnaryExpression createExpression(YExpression baseExpression);
    }

    private final YUnaryExpression.Kind kind;

    YUnaryExpression(YUnaryExpression.Kind kind, YExpression baseExpression) {
        super(baseExpression);
        this.kind = kind;
    }

    public YUnaryExpression.Kind getKind() {
        return kind;
    }

    public YExpression getExpression() {
        return getElements().get(0);
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(getExpression());
    }

    public String toString() {
        return "" + getKind() + getExpression();
    }
}
