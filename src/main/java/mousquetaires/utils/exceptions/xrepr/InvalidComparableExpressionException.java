package mousquetaires.utils.exceptions.xrepr;

public class InvalidComparableExpressionException extends XCompilationException {

    public InvalidComparableExpressionException(String originalExpr) {
        super("Cannot compare expression '" + originalExpr + "'");
    }
}
