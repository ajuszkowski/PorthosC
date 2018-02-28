package mousquetaires.languages.syntax.zformula;

public class ZVariableLocal extends ZVariableGlobal implements ZAtom {
    final int uniqueIndex;

    public ZVariableLocal(String name, int uniqueIndex) {
        super(name);
        this.uniqueIndex = uniqueIndex;
    }

    //String.format("%s_%d", name, index)

    public int getUniqueIndex() {
        return uniqueIndex;
    }
}
