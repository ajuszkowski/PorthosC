package mousquetaires.utils.exceptions.xgraph;

public class InvalidComparableExpressionException extends XCompilationException {

    public InvalidComparableExpressionException(String originalExpr) {
        super("Cannot compare expression '" + originalExpr + "'");
    }
}
