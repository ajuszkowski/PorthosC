package mousquetaires.utils.exceptions.xgraph;

public class InvalidComparableExpressionError extends XCompilationError {

    public InvalidComparableExpressionError(String originalExpr) {
        super("Cannot compare expression '" + originalExpr + "'");
    }
}
