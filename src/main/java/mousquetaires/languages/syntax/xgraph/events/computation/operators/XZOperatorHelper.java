package mousquetaires.languages.syntax.xgraph.events.computation.operators;

import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;


public class XZOperatorHelper {
    public static XZOperator convert(YRelativeBinaryExpression.Kind yOperator) {
        switch (yOperator) {
            case Equals:
                return XZOperator.CompareEquals;
            case NotEquals:
                return XZOperator.CompareNotEquals;
            case Greater:
                return XZOperator.CompareGreater;
            case GreaterOrEquals:
                return XZOperator.CompareGreaterOrEquals;
            case Less:
                return XZOperator.CompareLess;
            case LessOrEquals:
                return XZOperator.CompareLessOrEquals;
            default:
                throw new IllegalArgumentException(yOperator.name());
        }
    }

}
