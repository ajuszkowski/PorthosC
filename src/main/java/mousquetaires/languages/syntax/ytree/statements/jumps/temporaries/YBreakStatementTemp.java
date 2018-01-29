package mousquetaires.languages.syntax.ytree.statements.jumps.temporaries;

import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpLabel;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;


public class YBreakStatementTemp extends YJumpStatement {

    public YBreakStatementTemp() {
        super(new YJumpLabel("(closest_loop)"));
    }
}
