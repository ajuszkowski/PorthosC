package mousquetaires.languages.syntax.ytree.litmus;

import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YPostludeStatement extends YUnlabeledStatement {

    private final YAssertionStatement assertionStatement;

    public YPostludeStatement(YAssertionStatement assertionStatement) {
        this.assertionStatement = assertionStatement;
    }

    public YAssertionStatement getAssertionStatement() {
        return assertionStatement;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
