package mousquetaires.languages.internal.statements;

import mousquetaires.languages.internal.expressions.InternalExpression;


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
}
