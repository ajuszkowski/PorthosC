package mousquetaires.languages.ytree.expressions;

public class YNotExpression extends YUnaryExpression {

    public YNotExpression(YExpression expression) {
        super(OperatorKind.Not, expression);
    }
}
