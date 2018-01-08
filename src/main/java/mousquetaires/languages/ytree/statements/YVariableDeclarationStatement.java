package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.ytree.expressions.lvalue.YVariableRef;
import mousquetaires.languages.ytree.types.InternalType;


public class YVariableDeclarationStatement extends YStatement {

    public final InternalType type;
    public final YVariableRef variable;

    public YVariableDeclarationStatement(InternalType type, YVariableRef variable) {
        this.type = type;
        this.variable = variable;
    }
}
