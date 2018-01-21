package mousquetaires.languages.syntax.ytree.expressions.unary;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;


public abstract class YUnaryPostfixExpression extends YUnaryExpression {

    public YUnaryPostfixExpression(Kind kind, YExpression baseExpression) {
        super(kind, baseExpression);
    }

    @Override
    public String toString() {
        return "" + getExpression() + getKind();
    }
}
