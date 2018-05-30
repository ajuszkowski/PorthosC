package mousquetaires.languages.syntax.ytree.litmus;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


public final class YPostludeDefinition extends YStatement {

    private final YExpression expression; //a recursive boolean expression-tree

    public YPostludeDefinition(Origin origin, YExpression expression) {
        super(origin);
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
