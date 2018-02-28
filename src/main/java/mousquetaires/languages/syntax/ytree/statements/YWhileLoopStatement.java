package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


// TODO: separate while+for and dowhile loops!
public class YWhileLoopStatement extends YStatement {

    private final YExpression condition;
    private final YStatement body;


    public YWhileLoopStatement(YExpression condition, YStatement body) {
        this(newLabel(), condition, body);
    }

    private YWhileLoopStatement(String label, YExpression condition, YStatement body) {
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
    public YWhileLoopStatement withLabel(String newLabel) {
        return new YWhileLoopStatement(newLabel, getCondition(), getBody());
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(getCondition(), getBody());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YWhileLoopStatement copy() {
        return new YWhileLoopStatement(getLabel(), getCondition(), getBody());
    }

    @Override
    public String toString() {
        return String.format("while (%s) { %s }", getCondition(), getBody());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YWhileLoopStatement)) return false;
        YWhileLoopStatement that = (YWhileLoopStatement) o;
        return Objects.equals(getCondition(), that.getCondition()) &&
                Objects.equals(getBody(), that.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCondition(), getBody());
    }
}
