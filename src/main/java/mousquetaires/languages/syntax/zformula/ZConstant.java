package mousquetaires.languages.syntax.zformula;

public class ZConstant implements ZAtom {
    private final Object value;   // TODO: add type/bitness

    public ZConstant(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
