package mousquetaires.languages.syntax.ytree.statements.jumps.temporaries;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpLabel;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;


public class YReturnStatementTemp extends YJumpStatement {

    private final YExpression returnValue;

    public YReturnStatementTemp() {  //YExpression returnValue) {
        super(new YJumpLabel("(function)"));
        this.returnValue = null;
    }

    public YExpression getReturnValue() {
        return returnValue;
    }
}
