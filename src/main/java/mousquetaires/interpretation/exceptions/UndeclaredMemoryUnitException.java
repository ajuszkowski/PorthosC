package mousquetaires.interpretation.exceptions;


public class UndeclaredMemoryUnitException extends InterpreterException {

    public UndeclaredMemoryUnitException(String memoryUnitName) {
        super("Could not find memory unit: " + memoryUnitName);
    }
}
