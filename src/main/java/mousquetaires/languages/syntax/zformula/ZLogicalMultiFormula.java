package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;


public abstract class ZLogicalMultiFormula<T extends ZEntity> implements ZLogicalFormula {

    private final ImmutableList<T> expressions;

    protected ZLogicalMultiFormula(ImmutableList<T> expressions) {
        this.expressions = expressions;
    }

    protected ImmutableList<T> getExpressions() {
        return expressions;
    }

    protected abstract String operatorToString();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        if (expressions.size() == 1) {
            // unary operator
            sb.append(operatorToString()).append(expressions.get(0));
        }
        else {
            // multi-ary operator
            boolean addSeparator = false;
            for (T expression : getExpressions()) {
                if (addSeparator) {
                    sb.append(" ").append(operatorToString()).append(" ");
                }
                sb.append(expression);
                addSeparator = true;
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
