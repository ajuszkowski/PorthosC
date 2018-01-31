package mousquetaires.languages.syntax.ytree.specific;

import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.visitors.ytree.YtreeVisitor;


public abstract class YPostludeStatement extends YUnlabeledStatement {

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
