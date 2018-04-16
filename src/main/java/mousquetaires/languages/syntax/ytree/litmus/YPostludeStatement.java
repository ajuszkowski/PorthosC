package mousquetaires.languages.syntax.ytree.litmus;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public class YPostludeStatement extends YUnlabeledStatement {

    private final YAssertionStatement assertionStatement;

    public YPostludeStatement(YAssertionStatement assertionStatement) {
        this.assertionStatement = assertionStatement;
    }

    public YAssertionStatement getAssertionStatement() {
        return assertionStatement;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(getAssertionStatement());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
