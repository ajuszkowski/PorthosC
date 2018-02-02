package mousquetaires.utils.exceptions.xgraph;


public class UndeclaredMemoryUnitError extends XCompilationError {

    public UndeclaredMemoryUnitError(String memoryUnitName) {
        super("Could not find memoryevents unit: " + memoryUnitName);
    }
}
