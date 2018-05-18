package mousquetaires.languages.syntax.ytree.litmus;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


public final class YPostludeStatement extends YUnlabeledStatement {

    private final YExpression expression; //a recursive boolean expression-tree

    public YPostludeStatement(CodeLocation location, YExpression expression) {
        super(location);
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
        return "exists( " + getExpression() + " )";
    }
}
