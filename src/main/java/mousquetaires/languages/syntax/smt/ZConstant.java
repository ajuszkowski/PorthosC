package mousquetaires.languages.syntax.smt;

public class ZConstant implements ZAtom {
    private final Object value;

    public ZConstant(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
