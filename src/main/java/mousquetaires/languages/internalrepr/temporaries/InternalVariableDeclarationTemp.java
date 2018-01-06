package mousquetaires.languages.internalrepr.temporaries;

import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.expressions.InternalExpression;


public class InternalVariableDeclarationTemp implements InternalEntity {

    public final String variableName;
    public final InternalExpression expression;

    public InternalVariableDeclarationTemp(String variableName, InternalExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }
}
