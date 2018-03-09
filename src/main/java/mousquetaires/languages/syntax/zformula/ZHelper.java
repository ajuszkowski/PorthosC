package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;


public class ZHelper {

    public static ZBoolFormula and(ZBoolFormula... expressions) {
        return new ZBoolConjunction(expressions);
    }
    public static ZBoolFormula and(Iterable<? extends ZBoolFormula> expressions) {
        return new ZBoolConjunction(ImmutableList.copyOf(expressions));
    }

    public static ZBoolFormula or(ZBoolFormula... expressions) {
        return new ZBoolDisjunction(expressions);
    }
    public static ZBoolFormula or(Iterable<? extends ZBoolFormula> expressions) {
        return new ZBoolDisjunction(ImmutableList.copyOf(expressions));
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
