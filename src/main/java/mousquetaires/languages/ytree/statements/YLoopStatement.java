package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.ytree.expressions.YExpression;

import java.util.Objects;


public class YLoopStatement extends YStatement {

    public final YExpression condition;
    public final YStatement body;

    public YLoopStatement(YExpression condition, YStatement body) {
        this.condition = condition;
        this.body = body;
    }

    //@Override
    //public Iterable<YEntity> iterateChildren() {
    //    return List.of(condition, body);
    //}

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
