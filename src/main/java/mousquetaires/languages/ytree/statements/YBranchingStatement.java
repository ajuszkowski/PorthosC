package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.utils.YtreeUtils;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Objects;


public class YBranchingStatement extends YStatement {

    public final YExpression condition;

    public final YStatement thenBranch;

    @Nullable
    public final YStatement elseBranch;

    public YBranchingStatement(YExpression condition, YStatement thenBranch) {
        this(condition, thenBranch, null);
    }

    public YBranchingStatement(YExpression condition,
                               YStatement thenBranch,
                               YStatement elseBranch) {
        this(null, condition, thenBranch, elseBranch);
    }

    private YBranchingStatement(String label,
                                YExpression condition,
                                YStatement thenBranch,
                                YStatement elseBranch) {
        super(label);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public YBranchingStatement withLabel(String newLabel) {
        return new YBranchingStatement(newLabel, condition, thenBranch, elseBranch);
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(condition, thenBranch, elseBranch);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YBranchingStatement copy() {
        return new YBranchingStatement(label, condition, thenBranch, elseBranch);
    }

    @Override
    public String toString() {
        return String.format("if (%s) %s else %s", condition, thenBranch, elseBranch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YBranchingStatement)) return false;
        YBranchingStatement that = (YBranchingStatement) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(thenBranch, that.thenBranch) &&
                Objects.equals(elseBranch, that.elseBranch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, thenBranch, elseBranch);
    }
}
