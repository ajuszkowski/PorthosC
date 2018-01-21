package mousquetaires.languages.syntax.ytree.specific;

import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.visitors.YtreeVisitor;


public abstract class YPostludeStatement extends YStatement {

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
