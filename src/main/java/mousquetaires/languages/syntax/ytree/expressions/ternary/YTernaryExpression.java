package mousquetaires.languages.syntax.ytree.expressions.ternary;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;

// TODO: inherit from multi expression and use in parser
public class YTernaryExpression implements YExpression {
    private final YExpression condition;
    private final YExpression trueExpression;
    private final YExpression falseExpression;

    public YTernaryExpression(YExpression condition, YExpression trueExpression, YExpression falseExpression) {
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    public YExpression getCondition() {
        return condition;
    }

    public YExpression getTrueExpression() {
        return trueExpression;
    }

    public YExpression getFalseExpression() {
        return falseExpression;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(condition, trueExpression, falseExpression);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YTernaryExpression copy() {
        return new YTernaryExpression(condition, trueExpression, falseExpression);
    }

    @Override
    public String toString() {
        final StringBuffer builder = new StringBuffer();
        builder.append(condition)     .append(" ? ");
        builder.append(trueExpression).append(" : ");
        builder.append(falseExpression);
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YTernaryExpression)) return false;
        YTernaryExpression that = (YTernaryExpression) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(trueExpression, that.trueExpression) &&
                Objects.equals(falseExpression, that.falseExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, trueExpression, falseExpression);
    }
}
