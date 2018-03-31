package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;


public abstract class ZBoolMultiFormula<T extends ZEntity> implements ZBoolFormula {

    private final ImmutableList<T> expressions;

    protected ZBoolMultiFormula(ImmutableList<T> expressions) {
        this.expressions = expressions;
    }

    protected ImmutableList<T> getExpressions() {
        return expressions;
    }

    protected abstract String getOperatorText();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        if (expressions.size() == 1) {
            // unary operator
            sb.append(getOperatorText()).append(expressions.get(0));
        }
        else {
            // multi-ary operator
            boolean addSeparator = false;
            for (T expression : getExpressions()) {
                if (addSeparator) {
                    sb.append(" ").append(getOperatorText()).append(" ");
                }
                sb.append(expression);
                addSeparator = true;
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
