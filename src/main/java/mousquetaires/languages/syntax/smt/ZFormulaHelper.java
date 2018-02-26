package mousquetaires.languages.syntax.smt;

import mousquetaires.languages.syntax.xgraph.events.XEvent;


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

    public static ZBoolVariableGlobal getEventVariable(XEvent event) {
        return new ZBoolVariableGlobal(event.getUniqueId());
    }

}
