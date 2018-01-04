package mousquetaires.languages.internal.statements;

import mousquetaires.languages.internal.expressions.Expression;


public class LoopStatement extends Statement {
    public final Expression condition;

    public final Statement body;

    public LoopStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("while (%s) { %s }", condition, body);
    }
}
