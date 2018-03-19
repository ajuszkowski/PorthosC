package mousquetaires.languages.syntax.zformula;

import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZVariable)) return false;
        ZVariable zVariable = (ZVariable) o;
        return Objects.equals(getName(), zVariable.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }
}
