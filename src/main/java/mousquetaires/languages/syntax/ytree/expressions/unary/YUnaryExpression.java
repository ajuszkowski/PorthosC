package mousquetaires.languages.syntax.ytree.expressions.unary;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


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
    public Iterator<YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(getExpression());
    }

    public String toString() {
        return "" + getKind() + getExpression();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YUnaryExpression)) return false;
        if (!super.equals(o)) return false;
        YUnaryExpression that = (YUnaryExpression) o;
        return Objects.equals(getKind(), that.getKind());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKind());
    }
}
