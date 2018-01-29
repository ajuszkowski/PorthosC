package mousquetaires.languages.syntax.ytree.statements.jumps;

import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpLabel;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;


public class YContinueStatement extends YJumpStatement {

    public YContinueStatement() {
        super(new YJumpLabel("(current_loop_entry)"));
    }
}
