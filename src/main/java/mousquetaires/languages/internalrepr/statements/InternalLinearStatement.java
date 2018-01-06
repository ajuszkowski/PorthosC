package mousquetaires.languages.internalrepr.statements;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;


public class InternalLinearStatement extends InternalStatement {
    public final InternalExpression expression;

    public InternalLinearStatement(InternalExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression.toString() + ';';
    }
}
