package mousquetaires.languages.syntax.ytree.litmus;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


public final class YPreludeStatement extends YUnlabeledStatement {

    private final ImmutableList<YStatement> initialWrites;

    public YPreludeStatement(Origin location, ImmutableList<YStatement> initialWrites) {
        super(location);
        this.initialWrites = initialWrites;
    }

    public ImmutableList<YStatement> getInitialWrites() {
        return initialWrites;
    }

    @Override
    public String toString() {
        return "init { " + initialWrites + " }";
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
