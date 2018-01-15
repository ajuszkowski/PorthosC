package mousquetaires.utils.exceptions.xrepr;


public class UndeclaredMemoryUnitException extends InterpretationException {

    public UndeclaredMemoryUnitException(String memoryUnitName) {
        super("Could not find memory unit: " + memoryUnitName);
    }
}
