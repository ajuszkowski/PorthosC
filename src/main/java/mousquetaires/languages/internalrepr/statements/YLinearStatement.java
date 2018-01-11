package mousquetaires.languages.internalrepr.statements;

import mousquetaires.languages.internalrepr.expressions.YExpression;

import java.util.Objects;


public class YLinearStatement extends YStatement {
    public final YExpression expression;

    public YLinearStatement(YExpression expression) {
        this.expression = expression;
    }

    public YLinearStatement() {
        this.expression = null;  // empty statement
    }

    @Override
    public String toString() {
        return expression.toString() + ';';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YLinearStatement)) return false;
        YLinearStatement that = (YLinearStatement) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
