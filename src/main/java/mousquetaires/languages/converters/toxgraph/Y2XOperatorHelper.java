package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.syntax.ytree.expressions.binary.YBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YIntegerBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YLogicalBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YIntegerUnaryExpression;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;


class Y2XOperatorHelper {

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

}
