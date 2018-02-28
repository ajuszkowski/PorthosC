package mousquetaires.utils.exceptions.xgraph;


public class UndeclaredMemoryUnitError extends XInterpretationError {

    public UndeclaredMemoryUnitError(String memoryUnitName) {
        super("Could not find memoryevents unit: " + memoryUnitName);
    }
}
