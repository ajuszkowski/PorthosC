package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YLinearStatement extends YStatement {

    private final YExpression expression;

    public YLinearStatement(YExpression expression) {
        this(newLabel(), expression);
    }

    private YLinearStatement(String label, YExpression expression) {
        super(label);
        this.expression = expression;
    }

    public YExpression getExpression() {
        return expression;
    }

    public static YLinearStatement createEmptyStatement() {
        return new YLinearStatement(null);
    }

    @Override
    public YLinearStatement withLabel(String newLabel) {
        return new YLinearStatement(newLabel, getExpression());
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(getExpression());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YLinearStatement copy() {
        return new YLinearStatement(getLabel(), getExpression());
    }

    @Override
    public String toString() {
        return getExpression() + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YLinearStatement)) return false;
        YLinearStatement that = (YLinearStatement) o;
        return Objects.equals(getExpression(), that.getExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpression());
    }
}
