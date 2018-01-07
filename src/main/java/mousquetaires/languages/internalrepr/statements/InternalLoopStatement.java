package mousquetaires.languages.internalrepr.statements;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;

import java.util.Objects;


public class InternalLoopStatement extends InternalStatement {

    public final InternalExpression condition;
    public final InternalStatement body;

    public InternalLoopStatement(InternalExpression condition, InternalStatement body) {
        this.condition = condition;
        this.body = body;
    }

    //@Override
    //public Iterable<InternalEntity> iterateChildren() {
    //    return List.of(condition, body);
    //}

    @Override
    public String toString() {
        return String.format("while (%s) { %s }", condition, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalLoopStatement)) return false;
        InternalLoopStatement that = (InternalLoopStatement) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(condition, body);
    }
}
