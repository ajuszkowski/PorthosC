package mousquetaires.languages.internal.statements;

import mousquetaires.languages.internal.expressions.Expression;

import javax.annotation.Nullable;


public class BranchingStatement extends Statement {
    public final Expression condition;

    public final Statement trueBranch;

    @Nullable
    public final Statement falseBranch;

    public BranchingStatement(Expression condition,
                              Statement trueBranch,
                              Statement falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public String toString() {
        return String.format("if (%s) { %s } else { %s }", condition, trueBranch, falseBranch);
    }
}
