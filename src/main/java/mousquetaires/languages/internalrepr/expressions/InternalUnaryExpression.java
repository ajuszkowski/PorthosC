package mousquetaires.languages.internalrepr.expressions;

public class InternalUnaryExpression extends InternalExpression {
    public enum OperatorKind {
        Not, // !x
        IncrementPrefix, // ++x
        IncrementPostfix, // x++
        DecrementPrefix, // --x
        DecrementPostfix, // x--
        PointerDereference,  // &x
    }

    public final OperatorKind operator;
    public final InternalExpression expression;

    public InternalUnaryExpression(OperatorKind operator, InternalExpression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        switch (operator) {
            case Not:
                return "!" + expression;
            case IncrementPrefix:
                return "++" + expression;
            case IncrementPostfix:
                return expression + "++";
            case DecrementPrefix:
                return "--" + expression;
            case DecrementPostfix:
                return expression + "--";
            case PointerDereference:
                return "&" + expression;
            default:
                throw new IllegalArgumentException();
        }
    }
}
