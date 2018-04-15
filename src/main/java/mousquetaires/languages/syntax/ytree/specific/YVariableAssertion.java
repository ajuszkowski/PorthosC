package mousquetaires.languages.syntax.ytree.specific;

import mousquetaires.languages.syntax.ytree.expressions.YMemoryLocation;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariable;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;


public class YVariableAssertion extends YRelativeBinaryExpression {

    public YVariableAssertion(YVariable variable, YMemoryLocation value) {
        super(Kind.Equals, variable, value);
    }

    @Override
    public YVariable getLeftExpression() {
        return (YVariable) super.getLeftExpression();
    }

    @Override
    public YMemoryLocation getRightExpression() {
        return (YMemoryLocation) super.getRightExpression();
    }

}
