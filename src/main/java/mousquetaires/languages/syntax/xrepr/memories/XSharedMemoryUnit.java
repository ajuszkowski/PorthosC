package mousquetaires.languages.syntax.xrepr.memories;

public class XSharedMemoryUnit extends XMemoryUnit {

    XSharedMemoryUnit(String name, Bitness bitness) {
        super(name, bitness);
    }

    @Override
    public String toString() {
        return "shared " + super.toString();
    }
}
