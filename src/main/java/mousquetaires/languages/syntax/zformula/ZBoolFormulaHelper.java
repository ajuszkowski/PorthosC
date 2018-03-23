package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.utils.CollectionUtils;

import java.util.Collection;
import java.util.List;


public class ZBoolFormulaHelper {

    public static ZBoolFormula and(ZBoolFormula... expressions) {
        return and(List.of(expressions));
    }

    public static ZBoolFormula and(Collection<ZBoolFormula> expressions) {
        switch (expressions.size()) {
            case 0:
                return ZBoolConstant.True;
            case 1:
                return CollectionUtils.getSingleElement(expressions);
            default:
                return new ZBoolConjunction(ImmutableList.copyOf(expressions));
        }
    }

    public static ZBoolFormula or(ZBoolFormula... expressions) {
        return or(List.of(expressions));
    }

    public static ZBoolFormula or(Collection<? extends ZBoolFormula> expressions) {
        switch (expressions.size()) {
            case 0:
                return ZBoolConstant.True;
            case 1:
                return CollectionUtils.getSingleElement(expressions);
            default:
                return new ZBoolDisjunction(ImmutableList.copyOf(expressions));
        }
    }

    public static ZBoolFormula not(ZBoolFormula expression) {
        return expression instanceof ZBoolNegationAtom
                ? ((ZBoolNegationAtom) expression).getExpression()
                : new ZBoolNegationAtom(expression);
    }

    public static ZBoolImplication implies(ZBoolFormula left, ZBoolFormula right) {
        return new ZBoolImplication(left, right);
    }

    public static ZBoolExpression equals(ZAtom leftExpression, ZAtom rightExpression) {
        return new ZBoolExpression(XZOperator.CompareEquals, leftExpression, rightExpression);
    }
}
