package mousquetaires.languages.internallang_deleteme.statements;

import mousquetaires.languages.internallang_deleteme.expressions.InternalExpression;


public class InternalLoopStatement extends InternalStatement {

    public final InternalExpression condition;

    public InternalLoopStatement(InternalExpression expression, InternalExpression condition) {
        super(expression);
        this.condition = condition;
    }
}
