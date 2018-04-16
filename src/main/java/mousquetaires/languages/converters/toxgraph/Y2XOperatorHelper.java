package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.syntax.ytree.expressions.operations.YUnaryOperator;


class Y2XOperatorHelper {

    static boolean isPrefixOperator(YUnaryOperator operator) {
        return isPrefixIncrement(operator) || isPrefixDecrement(operator);
    }

    static boolean isPrefixIncrement(YUnaryOperator operator) {
        return operator == YUnaryOperator.PrefixIncrement;
    }

    static boolean isPrefixDecrement(YUnaryOperator operator) {
        return operator == YUnaryOperator.PrefixDecrement;
    }


    static boolean isPostfixOperator(YUnaryOperator operator) {
        return isPostfixIncrement(operator) || isPostfixDecrement(operator);
    }

    static boolean isPostfixIncrement(YUnaryOperator operator) {
        return operator == YUnaryOperator.PostfixIncrement;
    }

    static boolean isPostfixDecrement(YUnaryOperator operator) {
        return operator == YUnaryOperator.PostfixDecrement;
    }

}
