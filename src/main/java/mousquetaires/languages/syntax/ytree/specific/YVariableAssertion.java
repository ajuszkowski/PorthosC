package mousquetaires.languages.syntax.ytree.specific;

import mousquetaires.languages.syntax.ytree.expressions.YMemoryLocation;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;


public class YVariableAssertion extends YRelativeBinaryExpression {

    public YVariableAssertion(YVariableRef variable, YMemoryLocation value) {
        super(Kind.Equals, variable, value);
    }

    @Override
    public YVariableRef getLeftExpression() {
        return (YVariableRef) super.getLeftExpression();
    }

    @Override
    public YMemoryLocation getRightExpression() {
        return (YMemoryLocation) super.getRightExpression();
    }

    @Override
    public YVariableAssertion copy() {
        return new YVariableAssertion(getLeftExpression(), getRightExpression());
    }

}
