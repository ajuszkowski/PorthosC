package mousquetaires.languages.syntax.ytree.expressions.operations;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Iterator;
import java.util.Objects;


public class YBinaryExpression extends YMultiExpression {

    private final YBinaryOperator operator;

    YBinaryExpression(YBinaryOperator operator, YExpression leftExpression, YExpression rightExpression) {
        super(leftExpression, rightExpression);
        this.operator = operator;
    }

    public YBinaryOperator getOperator() {
        return operator;
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

    @Override
    public YExpression withPointerLevel(int level) {
        throw new NotSupportedException("binary expression cannot be a pointer");//todo: is that true?
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YBinaryExpression)) return false;
        if (!super.equals(o)) return false;
        YBinaryExpression that = (YBinaryExpression) o;
        return Objects.equals(getOperator(), that.getOperator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOperator());
    }
}
