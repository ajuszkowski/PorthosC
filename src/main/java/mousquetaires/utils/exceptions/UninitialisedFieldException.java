package mousquetaires.utils.exceptions;

public class UninitialisedFieldException extends IllegalStateException {

    public UninitialisedFieldException(String fieldName) {
        super("Field " + fieldName + " must be initialised.");
    }
}
