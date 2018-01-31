package mousquetaires.languages.syntax.xgraph.memories;

public class XLocation extends XMemoryUnitBase implements XSharedMemoryUnit {

    XLocation(String name, Bitness bitness) {
        super(name, bitness);
    }

    @Override
    public String toString() {
        return "(shared)" + super.toString();
    }
}
