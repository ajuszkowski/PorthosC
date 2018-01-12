package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.utils.YtreeUtils;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Objects;


public class YBranchingStatement extends YStatement {

    public final YExpression condition;

    public final YStatement trueBranch;

    @Nullable
    public final YStatement falseBranch;

    public YBranchingStatement(YExpression condition, YStatement trueBranch) {
        this(condition, trueBranch, null);
    }

    public YBranchingStatement(YExpression condition,
                               YStatement trueBranch,
                               YStatement falseBranch) {
        this(null, condition, trueBranch, falseBranch);
    }

    private YBranchingStatement(String label,
                                YExpression condition,
                                YStatement trueBranch,
                                YStatement falseBranch) {
        super(label);
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public YBranchingStatement withLabel(String newLabel) {
        return new YBranchingStatement(newLabel, condition, trueBranch, falseBranch);
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(condition, trueBranch, falseBranch);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("if (%s) %s else %s", condition, trueBranch, falseBranch);
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
