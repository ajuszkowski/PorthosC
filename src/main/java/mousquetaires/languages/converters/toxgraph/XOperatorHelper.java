package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.syntax.ytree.expressions.unary.YIntegerUnaryExpression;


class XOperatorHelper {

    public static boolean isPrefixOperator(YIntegerUnaryExpression.Kind operator) {
        return isPrefixIncrement(operator) || isPrefixDecrement(operator);
    }

    public static boolean isPrefixIncrement(YIntegerUnaryExpression.Kind operator) {
        return operator == YIntegerUnaryExpression.Kind.PrefixIncrement;
    }

    public static boolean isPrefixDecrement(YIntegerUnaryExpression.Kind operator) {
        return operator == YIntegerUnaryExpression.Kind.PrefixDecrement;
    }


    public static boolean isPostfixOperator(YIntegerUnaryExpression.Kind operator) {
        return isPostfixIncrement(operator) || isPostfixDecrement(operator);
    }

    public static boolean isPostfixIncrement(YIntegerUnaryExpression.Kind operator) {
        return operator == YIntegerUnaryExpression.Kind.PostfixIncrement;
    }

    public static boolean isPostfixDecrement(YIntegerUnaryExpression.Kind operator) {
        return operator == YIntegerUnaryExpression.Kind.PostfixDecrement;
    }


}
