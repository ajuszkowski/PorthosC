package mousquetaires.languages.internal.statements;

import mousquetaires.languages.internal.expressions.Expression;


public class LinearStatement extends Statement {
    public final Expression expression;

    public LinearStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression.toString() + ';';
    }
}
