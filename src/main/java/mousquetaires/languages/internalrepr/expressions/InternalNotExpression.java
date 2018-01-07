package mousquetaires.languages.internalrepr.expressions;

public class InternalNotExpression extends InternalUnaryExpression {

    public InternalNotExpression(InternalExpression expression) {
        super(OperatorKind.Not, expression);
    }
}
