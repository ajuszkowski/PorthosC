package mousquetaires.interpretation.internalrepr.exceptions;


public class UndeclaredVariableException extends InterpretationException {

    public UndeclaredVariableException(String locationName) {
        super(locationName);
    }
}
