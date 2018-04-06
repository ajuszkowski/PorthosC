package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.syntax.xgraph.events.computation.XOperator;
import mousquetaires.languages.syntax.ytree.expressions.binary.YBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YIntegerBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YLogicalBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YIntegerUnaryExpression;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;
import mousquetaires.languages.syntax.ytree.specific.YProcessStatement;


class YToXOperatorConverter {

    static boolean isPrefixOperator(YIntegerUnaryExpression.Kind operator) {
        return isPrefixIncrement(operator) || isPrefixDecrement(operator);
    }

    static boolean isPrefixIncrement(YIntegerUnaryExpression.Kind operator) {
        return operator == YIntegerUnaryExpression.Kind.PrefixIncrement;
    }

    static boolean isPrefixDecrement(YIntegerUnaryExpression.Kind operator) {
        return operator == YIntegerUnaryExpression.Kind.PrefixDecrement;
    }


    static boolean isPostfixOperator(YIntegerUnaryExpression.Kind operator) {
        return isPostfixIncrement(operator) || isPostfixDecrement(operator);
    }

    static boolean isPostfixIncrement(YIntegerUnaryExpression.Kind operator) {
        return operator == YIntegerUnaryExpression.Kind.PostfixIncrement;
    }

    static boolean isPostfixDecrement(YIntegerUnaryExpression.Kind operator) {
        return operator == YIntegerUnaryExpression.Kind.PostfixDecrement;
    }

    static XBinaryOperator convert(YBinaryExpression.Kind yOperator) {
        if (yOperator instanceof YRelativeBinaryExpression.Kind) {
            return convert((YRelativeBinaryExpression.Kind) yOperator);
        }
        if (yOperator instanceof YIntegerBinaryExpression.Kind) {
            return convert((YIntegerBinaryExpression.Kind) yOperator);
        }
        if (yOperator instanceof YLogicalBinaryExpression.Kind) {
            return convert((YLogicalBinaryExpression.Kind) yOperator);
        }
        throw new IllegalArgumentException(yOperator.toString());
    }


    static XBinaryOperator convert(YRelativeBinaryExpression.Kind yOperator) {
        switch (yOperator) {
            case Equals:            return XBinaryOperator.CompareEquals;
            case NotEquals:         return XBinaryOperator.CompareNotEquals;
            case Greater:           return XBinaryOperator.CompareGreater;
            case GreaterOrEquals:   return XBinaryOperator.CompareGreaterOrEquals;
            case Less:              return XBinaryOperator.CompareLess;
            case LessOrEquals:      return XBinaryOperator.CompareLessOrEquals;
            default:
                throw new IllegalArgumentException(yOperator.name());
        }
    }

    static XBinaryOperator convert(YIntegerBinaryExpression.Kind yOperator) {
        switch (yOperator) {
            case Plus:       return XBinaryOperator.Addition;
            case Minus:      return XBinaryOperator.Subtraction;
            case Multiply:   return XBinaryOperator.Multiplication;
            case Divide:     return XBinaryOperator.Division;
            case Modulo:     return XBinaryOperator.Modulo;
            case LeftShift:  return XBinaryOperator.LeftShift;
            case RightShift: return XBinaryOperator.RightShift;
            case BitAnd:     return XBinaryOperator.BitAnd;
            case BitOr:      return XBinaryOperator.BitOr;
            case BitXor:     return XBinaryOperator.BitXor;
            default:
                throw new IllegalArgumentException(yOperator.name());
        }
    }

    static XBinaryOperator convert(YLogicalBinaryExpression.Kind yOperator) {
        switch (yOperator) {
            case Conjunction:     return XBinaryOperator.Conjunction;
            case Disjunction:     return XBinaryOperator.Disjunction;
            default:
                throw new IllegalArgumentException(yOperator.name());
        }
    }
}
