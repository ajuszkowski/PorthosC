package mousquetaires.languages.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.visitors.YtreeVisitor;


public class YBlockStatement extends YSequenceStatement {

    public YBlockStatement(YStatement... statements) {
        this(null, ImmutableList.copyOf(statements));
    }

    public YBlockStatement(ImmutableList<YStatement> statements) {
        this(null, statements);
    }

    private YBlockStatement(String label, ImmutableList<YStatement> statements) {
        super(label, statements);
    }

    public static YBlockStatement create(YStatement statement) {
        if (statement instanceof YSequenceStatement) {
            YSequenceStatement sequenceStatement = (YSequenceStatement) statement;
            return new YBlockStatement(sequenceStatement.label, sequenceStatement.statements);
        }
        return new YBlockStatement(statement);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YBlockStatement copy() {
        return new YBlockStatement(label, statements);
    }

    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }
}
