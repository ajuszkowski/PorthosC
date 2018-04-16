package mousquetaires.languages.syntax.ytree.litmus;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public final class YAssertionStatement extends YUnlabeledStatement {

    private final YExpression expression; //a recursive boolean expression-tree

    public YAssertionStatement(YExpression expression) {
        this.expression = expression;
    }

    public YExpression getExpression() {
        return expression;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "exists( " + expression + " )";
    }
}
