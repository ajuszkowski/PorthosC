package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.ytree.expressions.lvalue.YVariableRef;
import mousquetaires.languages.ytree.types.YType;


public class YVariableDeclarationStatement extends YStatement {

    public final YType type;
    public final YVariableRef variable;

    public YVariableDeclarationStatement(YType type, YVariableRef variable) {
        this.type = type;
        this.variable = variable;
    }
}
