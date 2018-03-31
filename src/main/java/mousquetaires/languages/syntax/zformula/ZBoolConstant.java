package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public enum ZBoolConstant implements ZBoolFormula {
    True,
    False,
    ;

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
