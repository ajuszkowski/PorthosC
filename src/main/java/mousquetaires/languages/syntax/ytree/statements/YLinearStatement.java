package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Objects;


public class YLinearStatement extends YStatement {

    private final YExpression expression;

    public YLinearStatement(YExpression expression) {
        this(expression.origin(), null, expression);
    }

    private YLinearStatement(Origin origin, String label, YExpression expression) {
        super(origin, label);
        assert expression != null;//TODO: add non-null asserts everywhere
        this.expression = expression;
    }

    public YExpression getExpression() {
        return expression;
    }

    @Override
    public YLinearStatement withLabel(String newLabel) {
        return new YLinearStatement(origin(), newLabel, getExpression());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
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
