package mousquetaires.languages.syntax.ytree.litmus;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public class YPreludeStatement extends YUnlabeledStatement {

    private final ImmutableList<YAssignmentExpression> initialWrites;

    public YPreludeStatement(ImmutableList<YAssignmentExpression> initialWrites) {
        this.initialWrites = initialWrites;
    }

    public ImmutableList<YAssignmentExpression> getInitialWrites() {
        return initialWrites;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(getInitialWrites());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
