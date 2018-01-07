package mousquetaires.languages.internalrepr.statements;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;

import java.util.Objects;


public class InternalLinearStatement extends InternalStatement {
    public final InternalExpression expression;

    public InternalLinearStatement(InternalExpression expression) {
        this.expression = expression;
    }

    public InternalLinearStatement() {
        this.expression = null;  // empty statement
    }

    @Override
    public String toString() {
        return expression.toString() + ';';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalLinearStatement)) return false;
        InternalLinearStatement that = (InternalLinearStatement) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {

        return Objects.hash(expression);
    }
}
