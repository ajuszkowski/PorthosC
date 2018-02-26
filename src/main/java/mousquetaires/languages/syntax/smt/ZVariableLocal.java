package mousquetaires.languages.syntax.smt;

public class ZVariableLocal extends ZVariableGlobal implements ZAtom {
    final int uniqueIndex;

    public ZVariableLocal(String name, int uniqueIndex) {
        super(name);
        this.uniqueIndex = uniqueIndex;
    }

    public int getUniqueIndex() {
        return uniqueIndex;
    }
}
