package mousquetaires.languages.syntax.ytree.statements.jumps.temporaries;

import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpLabel;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;


public class YContinueStatementTemp extends YJumpStatement {

    public YContinueStatementTemp() {
        super(new YJumpLabel("(closest_loop)"));
    }
}
