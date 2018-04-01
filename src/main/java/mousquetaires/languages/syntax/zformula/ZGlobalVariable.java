package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.common.Bitness;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public final class ZGlobalVariable extends ZNamedAtomBase implements ZLogicalFormula {

    ZGlobalVariable(String name, Bitness bitness) {
        super(name, bitness);
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
