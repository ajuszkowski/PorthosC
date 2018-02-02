package mousquetaires.utils.exceptions.xgraph;


public class MemoryUnitDoubleDeclarationError extends XCompilationError {

    public MemoryUnitDoubleDeclarationError(String memoryUnitName, boolean isLocal) {
        super("Duplicating declaration of " + (isLocal ? "local" : "shared") + " memoryevents unit: " + memoryUnitName);
    }
}
