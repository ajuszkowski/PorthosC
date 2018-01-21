package mousquetaires.languages.syntax.ytree.specific;

import mousquetaires.languages.syntax.ytree.expressions.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;


public class YVariableAssertion extends YRelativeBinaryExpression {

    public final Integer processId;

    public YVariableAssertion(Integer processId, YVariableRef variable, YConstant value) {
        super(Kind.Equals, variable, value);
        this.processId = processId;
    }

    public YVariableAssertion(YVariableRef variable, YConstant value) {
        this(null, variable, value);
    }

    @Override
    public YVariableRef getLeftExpression() {
        return (YVariableRef) super.getLeftExpression();
    }

    @Override
    public YConstant getRightExpression() {
        return (YConstant) super.getRightExpression();
    }

    @Override
    public YVariableAssertion copy() {
        return new YVariableAssertion(processId, getLeftExpression(), getRightExpression());
    }

}
