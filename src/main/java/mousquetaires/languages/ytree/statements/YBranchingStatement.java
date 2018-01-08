package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.ytree.expressions.YExpression;

import javax.annotation.Nullable;
import java.util.Objects;


public class YBranchingStatement extends YStatement {

    public final YExpression condition;

    public final YStatement trueBranch;

    @Nullable
    public final YStatement falseBranch;

    public YBranchingStatement(YExpression condition,
                               YStatement trueBranch,
                               YStatement falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public YBranchingStatement(YExpression condition,
                               YStatement trueBranch) {
        this(condition, trueBranch, null);
    }

    @Override
    public String toString() {
        return String.format("if (%s) { %s } else { %s }", condition, trueBranch, falseBranch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YBranchingStatement)) return false;
        YBranchingStatement that = (YBranchingStatement) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(trueBranch, that.trueBranch) &&
                Objects.equals(falseBranch, that.falseBranch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, trueBranch, falseBranch);
    }
}
