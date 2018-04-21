package mousquetaires.languages.syntax.ytree.litmus;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public final class YPreludeStatement extends YUnlabeledStatement {

    private final ImmutableList<YAssignmentExpression> initialWrites;

    public YPreludeStatement(CodeLocation location, ImmutableList<YAssignmentExpression> initialWrites) {
        super(location);
        this.initialWrites = initialWrites;
    }

    public ImmutableList<YAssignmentExpression> getInitialWrites() {
        return initialWrites;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
