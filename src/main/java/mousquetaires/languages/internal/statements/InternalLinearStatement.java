package mousquetaires.languages.internal.statements;

import mousquetaires.languages.internal.expressions.InternalExpression;


public class InternalLinearStatement extends InternalStatement {
    public final InternalExpression expression;

    public InternalLinearStatement(InternalExpression expression) {
        this.expression = expression;
    }

    //@Override
    //public Iterable<InternalEntity> iterateChildren() {
    //    return List.of(expression);
    //}

    @Override
    public String toString() {
        return expression.toString() + ';';
    }
}
