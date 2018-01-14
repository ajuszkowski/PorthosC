package mousquetaires.interpretation.exceptions;


public class UndeclaredMemoryLocationException extends InterpreterException {

    public UndeclaredMemoryLocationException(String locationName) {
        super("Could not find memory location: " + locationName);
    }
}
