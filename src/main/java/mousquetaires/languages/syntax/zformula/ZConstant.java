package mousquetaires.languages.syntax.zformula;


import mousquetaires.languages.common.Bitness;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public class ZConstant extends ZAtomBase {

    private final Object value;

    ZConstant(Object value, Bitness bitness) {
        super(bitness);//todo: we do not need the name, do not inherit from ZNamedAtom
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "const_" + getValue();
    }
}
