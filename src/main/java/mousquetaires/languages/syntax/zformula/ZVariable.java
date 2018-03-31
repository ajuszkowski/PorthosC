package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public class ZVariable extends ZNamedAtom implements ZBoolFormula {

    ZVariable(String name) {
        super(name);
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
