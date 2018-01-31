package mousquetaires.languages.syntax.xrepr.memories;

public class XLocalMemoryUnit extends XMemoryUnit {

    protected XLocalMemoryUnit(String name, Bitness bitness) {
        super(name, bitness);
    }

    @Override
    public String toString() {
        return "local " + super.toString();
    }
}
