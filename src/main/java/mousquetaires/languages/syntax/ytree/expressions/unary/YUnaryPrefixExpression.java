package mousquetaires.languages.syntax.ytree.expressions.unary;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;


public abstract class YUnaryPrefixExpression extends YUnaryExpression {

    public YUnaryPrefixExpression(Kind kind, YExpression baseExpression) {
        super(kind, baseExpression);
    }

    @Override
    public String toString() {
        return "" + getKind() + getExpression();
    }
}
