package mousquetaires.languages.internallang_deleteme.statements;

import mousquetaires.languages.internallang_deleteme.expressions.InternalExpression;


public abstract class InternalStatement {
    public final InternalExpression expression;

    public InternalStatement(InternalExpression expression) {
        this.expression = expression;
    }
}
