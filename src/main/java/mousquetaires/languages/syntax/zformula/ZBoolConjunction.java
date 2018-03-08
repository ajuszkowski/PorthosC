package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;


public class ZBoolConjunction extends ZBoolMultiFormula implements ZBoolFormula {

    public ZBoolConjunction(ZBoolFormula... expressions) {
        this(ImmutableList.copyOf(expressions));
    }

    ZBoolConjunction(ImmutableList<ZBoolFormula> expressions) {
        super(expressions);
    }

}