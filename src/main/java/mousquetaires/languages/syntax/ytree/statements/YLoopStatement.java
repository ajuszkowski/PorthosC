package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YLoopStatement extends YStatement {

    private final YExpression condition;
    private final YStatement body;

    public YLoopStatement(YExpression condition, YStatement body) {
        this(newLabel(), condition, body);
    }

    private YLoopStatement(String label, YExpression condition, YStatement body) {
        super(label);
        this.condition = condition;
        this.body = body;
    }

    public YExpression getCondition() {
        return condition;
    }

    public YStatement getBody() {
        return body;
    }

    @Override
    public YLoopStatement withLabel(String newLabel) {
        return new YLoopStatement(newLabel, getCondition(), getBody());
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(getCondition(), getBody());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YLoopStatement copy() {
        return new YLoopStatement(getLabel(), getCondition(), getBody());
    }

    @Override
    public String toString() {
        return String.format("while (%s) { %s }", getCondition(), getBody());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YLoopStatement)) return false;
        YLoopStatement that = (YLoopStatement) o;
        return Objects.equals(getCondition(), that.getCondition()) &&
                Objects.equals(getBody(), that.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCondition(), getBody());
    }
}
