package mousquetaires.languages.syntax.zformula;

public abstract class ZVariable implements ZAtom {
    private final String name;

    ZVariable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
