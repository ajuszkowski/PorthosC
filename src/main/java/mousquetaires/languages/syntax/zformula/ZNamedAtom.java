package mousquetaires.languages.syntax.zformula;

import java.util.Objects;


public abstract class ZNamedAtom implements ZFormula {
    private final String name;

    ZNamedAtom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZNamedAtom)) return false;
        ZNamedAtom other = (ZNamedAtom) o;
        return Objects.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }
}
