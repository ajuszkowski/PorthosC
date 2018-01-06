package mousquetaires.languages.internalrepr.statements;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;

import javax.annotation.Nullable;


public class InternalBranchingStatement extends InternalStatement {

    public final InternalExpression condition;

    public final InternalStatement trueBranch;

    @Nullable
    public final InternalStatement falseBranch;

    public InternalBranchingStatement(InternalExpression condition,
                                      InternalStatement trueBranch,
                                      InternalStatement falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public InternalBranchingStatement(InternalExpression condition,
                                      InternalStatement trueBranch) {
        this(condition, trueBranch, null);
    }

    @Override
    public String toString() {
        return String.format("if (%s) { %s } else { %s }", condition, trueBranch, falseBranch);
    }
}
