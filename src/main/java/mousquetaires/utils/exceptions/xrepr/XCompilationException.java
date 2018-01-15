package mousquetaires.utils.exceptions.xrepr;

public abstract class XCompilationException extends RuntimeException {

    public XCompilationException() {
    }

    public XCompilationException(String message) {
        super(message);
    }

    public XCompilationException(String message, Throwable cause) {
        super(message, cause);
    }

    public XCompilationException(Throwable cause) {
        super(cause);
    }

    public XCompilationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
