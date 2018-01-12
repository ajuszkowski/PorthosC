package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YLoopStatement extends YStatement {

    public final YExpression condition;
    public final YStatement body;

    public YLoopStatement(YExpression condition, YStatement body) {
        this(null, condition, body);
    }

    public YLoopStatement(String label, YExpression condition, YStatement body) {
        super(label);
        this.condition = condition;
        this.body = body;
    }

    //@Override
    //public Iterable<YEntity> iterateChildren() {
    //    return List.of(condition, body);
    //}

    @Override
    public YLoopStatement withLabel(String newLabel) {
        return new YLoopStatement(newLabel, condition, body);
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(condition, body);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YLoopStatement copy() {
        return new YLoopStatement(label, condition, body);
    }

    @Override
    public String toString() {
        return String.format("while (%s) { %s }", condition, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YLoopStatement)) return false;
        YLoopStatement that = (YLoopStatement) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, body);
    }
}
