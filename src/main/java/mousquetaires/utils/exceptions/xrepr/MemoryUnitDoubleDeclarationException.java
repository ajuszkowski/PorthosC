package mousquetaires.utils.exceptions.xrepr;


public class MemoryUnitDoubleDeclarationException extends InterpretationException {

    public MemoryUnitDoubleDeclarationException(String memoryUnitName, boolean isLocal) {
        super("Duplicating declaration of " + (isLocal ? "local" : "shared") + " memory unit: " + memoryUnitName);
    }
}
