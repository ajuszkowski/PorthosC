package mousquetaires.languages.syntax.zformula;


import mousquetaires.languages.common.Bitness;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;

import java.util.Objects;


public final class ZIndexedVariable extends ZNamedAtomBase implements ZFormula {
    private final int index;

    ZIndexedVariable(String name, Bitness bitness, int index) {
        super(name, bitness);
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
        return getName() + "_" + getIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZIndexedVariable)) return false;
        if (!super.equals(o)) return false;
        ZIndexedVariable that = (ZIndexedVariable) o;
        return getIndex() == that.getIndex();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getIndex());
    }
}
