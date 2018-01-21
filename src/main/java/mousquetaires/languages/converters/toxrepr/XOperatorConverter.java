package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.syntax.xrepr.events.computation.XOperator;
import mousquetaires.languages.syntax.ytree.expressions.binary.YIntegerBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YLogicalBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;


public class XOperatorConverter {
    public static XOperator convert(YIntegerBinaryExpression.Kind operator) {
        switch (operator) {
            case Plus:       return XOperator.IntegerPlus;
            case Minus:      return XOperator.IntegerMinus;
            case Multiply:   return XOperator.IntegerMultiply;
            case Divide:	 return XOperator.IntegerDivide;
            case Modulo:	 return XOperator.IntegerModulo;
            case LeftShift:	 return XOperator.IntegerLeftShift;
            case RightShift: return XOperator.IntegerRightShift;
            case BitAnd:	 return XOperator.BitAnd;
            case BitOr:	     return XOperator.BitOr;
            case BitXor:	 return XOperator.BitXor;
            case BitNot:	 return XOperator.BitNot;
            default: throw new IllegalArgumentException(operator.name());
        }
    }

    public static XOperator convert(YLogicalBinaryExpression.Kind operator) {
        switch (operator) {
            case Conjunction: return XOperator.LogicalAnd;
            case Disjunction: return XOperator.LogicalOr;
            default: throw new IllegalArgumentException(operator.name());
        }
    }
    
    public static XOperator convert(YRelativeBinaryExpression.Kind operator) {
        switch (operator) {
            case Equals:          return XOperator.CompareEquals;
            case NotEquals:       return XOperator.CompareNotEquals;
            case Greater:         return XOperator.CompareGreater;
            case GreaterOrEquals: return XOperator.CompareGreaterOrEquals;
            case Less:            return XOperator.CompareLess;
            case LessOrEquals:    return XOperator.CompareLessOrEquals;
            default: throw new IllegalArgumentException(operator.name());
        }
    }

    // todo: unary
}
