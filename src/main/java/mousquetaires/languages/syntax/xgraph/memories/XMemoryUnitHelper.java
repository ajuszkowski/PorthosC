package mousquetaires.languages.syntax.xgraph.memories;

public class XMemoryUnitHelper {

    public static boolean isSharedMemoryUnit(XMemoryUnit memoryUnit) {
        return memoryUnit instanceof XSharedMemoryUnit;
    }
}
