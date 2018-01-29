package mousquetaires.languages.syntax.ytree.statements.jumps;

import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpLabel;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;


public class YBreakStatement extends YJumpStatement {

    public YBreakStatement() {
        super(new YJumpLabel("(current_loop_exit)"));
    }
}
