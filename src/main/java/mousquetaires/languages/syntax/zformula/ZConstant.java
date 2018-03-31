package mousquetaires.languages.syntax.zformula;


import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public class ZConstant extends ZNamedAtom {
    ZConstant(String name) {
        super(name);
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
