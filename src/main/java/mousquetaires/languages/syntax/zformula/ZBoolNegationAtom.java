package mousquetaires.languages.syntax.zformula;


public class ZBoolNegationAtom implements ZBoolFormula, ZAtom {
    private final ZBoolFormula expression;

    ZBoolNegationAtom(ZBoolFormula expression) {
        this.expression = expression;
    }

    public ZBoolFormula getExpression() {
        return expression;
    }
}
