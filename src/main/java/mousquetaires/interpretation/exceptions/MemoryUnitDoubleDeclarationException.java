package mousquetaires.interpretation.exceptions;


public class MemoryUnitDoubleDeclarationException extends InterpreterException {

    public MemoryUnitDoubleDeclarationException(String memoryUnitName, boolean isLocal) {
        super("Duplicating declaration of " + (isLocal ? "local" : "shared") + " memory unit: " + memoryUnitName);
    }
}
