package mousquetaires.languages.internalrepr.statements;

import mousquetaires.languages.internalrepr.expressions.lvalue.InternalVariableRef;
import mousquetaires.languages.internalrepr.types.InternalType;


public class InternalVariableDeclarationStatement extends InternalStatement {

    public final InternalType type;
    public final InternalVariableRef variable;

    public InternalVariableDeclarationStatement(InternalType type, InternalVariableRef variable) {
        this.type = type;
        this.variable = variable;
    }
}
