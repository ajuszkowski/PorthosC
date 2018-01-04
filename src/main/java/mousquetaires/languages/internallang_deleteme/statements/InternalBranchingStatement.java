package mousquetaires.languages.internallang_deleteme.statements;

import mousquetaires.languages.internallang_deleteme.expressions.InternalExpression;


public class InternalBranchingStatement extends InternalStatement {

    public final InternalExpression condition;

    public InternalBranchingStatement(InternalExpression expression, InternalExpression condition) {
        super(expression);
        this.condition = condition;
    }
}
