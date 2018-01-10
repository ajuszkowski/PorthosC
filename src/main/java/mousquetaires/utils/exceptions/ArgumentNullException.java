package mousquetaires.utils.exceptions;

public class ArgumentNullException extends IllegalArgumentException {

    public ArgumentNullException() {
        this("?");
    }

    public ArgumentNullException(String argumentName) {
        super("Argument " + argumentName + " must have non-null value.");
    }
}
