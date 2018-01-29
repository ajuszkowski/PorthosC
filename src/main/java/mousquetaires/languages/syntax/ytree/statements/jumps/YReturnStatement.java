package mousquetaires.languages.syntax.ytree.statements.jumps;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;


public class YReturnStatement extends YJumpStatement {

    private final YExpression returnValue;

    public YReturnStatement() {  //TODO: //YExpression returnValue) {
        super(new YJumpLabel("(current_function_exit)"));
        this.returnValue = null;
    }

    public YExpression getReturnValue() {
        return returnValue;
    }
}
