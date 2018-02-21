package mousquetaires.utils.exceptions.encoding;

public class XEncoderIllegalStateException extends RuntimeException {

    public XEncoderIllegalStateException() {
    }

    public XEncoderIllegalStateException(String message) {
        super(message);
    }

    public XEncoderIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public XEncoderIllegalStateException(Throwable cause) {
        super(cause);
    }
}
