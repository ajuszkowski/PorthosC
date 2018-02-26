package mousquetaires.languages.syntax.smt;

import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;


public class ZFormulaHelper {

    public static ZBoolFormula and(ZBoolFormula... expressions) {
        return new ZBoolConjunction(expressions);
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
