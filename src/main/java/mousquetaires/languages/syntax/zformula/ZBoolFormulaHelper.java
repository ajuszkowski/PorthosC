package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;

import java.util.List;


public class ZBoolFormulaHelper {

    public static ZBoolFormula and(ZBoolFormula... expressions) {
        return and(List.of(expressions));
    }

    public static ZBoolFormula and(List<ZBoolFormula> expressions) {
        switch (expressions.size()) {
            case 0:
                return ZBoolConstant.True;
            case 1:
                return expressions.get(0);
            default:
                return new ZBoolConjunction(ImmutableList.copyOf(expressions));
        }
    }

    public static ZBoolFormula or(ZBoolFormula... expressions) {
        return or(List.of(expressions));
    }

    public static ZBoolFormula or(List<? extends ZBoolFormula> expressions) {
        switch (expressions.size()) {
            case 0:
                return ZBoolConstant.True;
            case 1:
                return expressions.get(0);
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

    public static ZBoolAtom equals(ZAtom leftExpression, ZAtom rightExpression) {
        return new ZBoolAtom(XZOperator.CompareEquals, leftExpression, rightExpression);
    }
}
