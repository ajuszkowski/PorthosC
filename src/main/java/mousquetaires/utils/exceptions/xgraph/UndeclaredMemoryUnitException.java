package mousquetaires.utils.exceptions.xgraph;


public class UndeclaredMemoryUnitException extends XCompilationException {

    public UndeclaredMemoryUnitException(String memoryUnitName) {
        super("Could not find memoryevents unit: " + memoryUnitName);
    }
}
