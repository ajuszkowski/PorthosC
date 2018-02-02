package mousquetaires.utils.exceptions.xgraph;

public class XCompilationError extends RuntimeException {

    public XCompilationError() {
    }

    public XCompilationError(String message) {
        super(message);
    }

    public XCompilationError(String message, Throwable cause) {
        super(message, cause);
    }

    public XCompilationError(Throwable cause) {
        super(cause);
    }
}
