package mousquetaires.utils.exceptions.encoding;

public class XVisitorIllegalStateException extends RuntimeException {

    public XVisitorIllegalStateException() {
    }

    public XVisitorIllegalStateException(String message) {
        super(message);
    }

    public XVisitorIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public XVisitorIllegalStateException(Throwable cause) {
        super(cause);
    }
}
