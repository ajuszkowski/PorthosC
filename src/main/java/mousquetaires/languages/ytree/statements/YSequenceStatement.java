package mousquetaires.languages.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YSequenceStatement extends YStatement {

    public final ImmutableList<YStatement> statements;

    public YSequenceStatement(YStatement... statements) {
        this(null, ImmutableList.copyOf(statements));
    }

    public YSequenceStatement(ImmutableList<YStatement> statements) {
        this(null, statements);
    }

    protected YSequenceStatement(String label, ImmutableList<YStatement> statements) {
        super(label);
        if (statements.size() == 0) {
            throw new IllegalArgumentException();
        }
        this.statements = statements;
    }

    @Override
    public YSequenceStatement withLabel(String newLabel) {
        return new YSequenceStatement(newLabel, this.statements);
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(statements);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new IllegalStateException("The constructed tree must have be no " + getClass().getName() + " nodes.");
    }

    @Override
    public YSequenceStatement copy() {
        return new YSequenceStatement(label, statements);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (YStatement statement : statements) {
            builder.append(statement.toString()).append(" ");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YSequenceStatement)) return false;
        YSequenceStatement that = (YSequenceStatement) o;
        return Objects.equals(statements, that.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }
}
