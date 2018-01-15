package mousquetaires.utils.exceptions.xrepr;


public class UndeclaredMemoryUnitException extends XCompilationException {

    public UndeclaredMemoryUnitException(String memoryUnitName) {
        super("Could not find memoryevents unit: " + memoryUnitName);
    }
}
