package mousquetaires.languages.syntax.ytree.expressions.binary;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


public abstract class YBinaryExpression extends YMultiExpression {
    private final YBinaryExpression.Kind kind;

    YBinaryExpression(YBinaryExpression.Kind kind, YExpression leftExpression, YExpression rightExpression) {
        super(leftExpression, rightExpression);
        this.kind = kind;
    }

    public YBinaryExpression.Kind getKind() {
        return kind;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(getLeftExpression(), getRightExpression());
    }

    public YExpression getLeftExpression() {
        return getElements().get(0);
    }

    public YExpression getRightExpression() {
        return getElements().get(1);
    }

    public interface Kind {
        YBinaryExpression createExpression(YExpression leftExpression, YExpression rightExpression);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YBinaryExpression)) return false;
        if (!super.equals(o)) return false;
        YBinaryExpression that = (YBinaryExpression) o;
        return Objects.equals(getKind(), that.getKind());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKind());
    }
}
