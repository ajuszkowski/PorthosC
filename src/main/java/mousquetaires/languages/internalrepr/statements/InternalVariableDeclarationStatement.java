package mousquetaires.languages.internalrepr.statements;

import mousquetaires.languages.internalrepr.variables.InternalVariable;


public class InternalVariableDeclarationStatement extends InternalStatement {

    public final InternalVariable variable;

    public InternalVariableDeclarationStatement(InternalVariable variable) {
        this.variable = variable;
    }
}
