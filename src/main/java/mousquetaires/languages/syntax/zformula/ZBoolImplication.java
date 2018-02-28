package mousquetaires.languages.syntax.zformula;

public class ZBoolImplication implements ZBoolFormula {

    private final ZBoolFormula leftExpression;
    private final ZBoolFormula rightExpression;

    public ZBoolImplication(ZBoolFormula leftExpression, ZBoolFormula rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public ZBoolFormula getLeftExpression() {
        return leftExpression;
    }

    public ZBoolFormula getRightExpression() {
        return rightExpression;
    }
}
