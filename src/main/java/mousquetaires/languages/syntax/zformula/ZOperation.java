package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;


public abstract class ZOperation extends ZLogicalMultiFormula<ZAtom> {

    private final ZOperator operator;

    public ZOperation(ZOperator operator, ImmutableList<ZAtom> expressions) {
        super(expressions);
        this.operator = operator;
    }

    protected ZOperator getOperator() {
        return operator;
    }

    @Override
    protected String operatorToString() {
        return getOperator().toString();
    }
}
