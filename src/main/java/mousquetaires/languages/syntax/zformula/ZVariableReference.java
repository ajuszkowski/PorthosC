package mousquetaires.languages.syntax.zformula;


import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;

import java.util.Objects;


public class ZVariableReference extends ZNamedAtom implements ZFormula {
    private final int index;

    ZVariableReference(String name, int index) {
        super(name);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "(" + getName() + ":" + getIndex() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZVariableReference)) return false;
        if (!super.equals(o)) return false;
        ZVariableReference that = (ZVariableReference) o;
        return getIndex() == that.getIndex();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getIndex());
    }
}
