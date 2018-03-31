package mousquetaires.languages.syntax.zformula.visitors;

public class ZformulaVisitorIllegalStateException extends RuntimeException {

    ZformulaVisitorIllegalStateException() {
    }

    ZformulaVisitorIllegalStateException(String message) {
        super(message);
    }

    ZformulaVisitorIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }

    ZformulaVisitorIllegalStateException(Throwable cause) {
        super(cause);
    }
}
