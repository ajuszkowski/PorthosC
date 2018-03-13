package mousquetaires.languages.syntax.zformula;

public class ZVariableReference extends ZVariable implements ZAtom {
    private final int index;

    public ZVariableReference(String name, int index) {
        super(name);
        this.index = index;
    }

    //String.format("%s_%d", name, index)

    public int getIndex() {
        return index;
    }
}
