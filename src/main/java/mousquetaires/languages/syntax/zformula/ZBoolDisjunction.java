package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;


public class ZBoolDisjunction extends ZBoolMultiFormula implements ZBoolFormula {

    public ZBoolDisjunction(ZBoolFormula... expressions) {
        this(ImmutableList.copyOf(expressions));
    }

    ZBoolDisjunction(ImmutableList<ZBoolFormula> expressions) {
        super(expressions);
    }

}
