package mousquetaires.interpretation.exceptions;

public class MemoryLocationNotFoundException extends InterpretationException {

    public MemoryLocationNotFoundException(String locationName) {
        super(locationName);
    }
}
