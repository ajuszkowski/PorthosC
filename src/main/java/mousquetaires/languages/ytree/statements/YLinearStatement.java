package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YLinearStatement extends YStatement {

    public final YExpression expression;

    public YLinearStatement(YExpression expression) {
        this(null, expression);
    }

    private YLinearStatement(String label, YExpression expression) {
        super(label);
        this.expression = expression;
    }

    @Override
    public YLinearStatement withLabel(String newLabel) {
        return new YLinearStatement(newLabel, expression);
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(expression);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return expression.toString() + ';';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YLinearStatement)) return false;
        YLinearStatement that = (YLinearStatement) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
