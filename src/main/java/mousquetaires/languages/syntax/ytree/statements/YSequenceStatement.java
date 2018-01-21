package mousquetaires.languages.syntax.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;

import java.util.Iterator;
import java.util.Objects;


public class YSequenceStatement extends YStatement {

    private final boolean hasBraces; // defines whether sequence of statements has surrounding braces '{' '}'
    private final ImmutableList<YStatement> statements;

    public YSequenceStatement(boolean hasBraces, YStatement... statements) {
        this(hasBraces, ImmutableList.copyOf(statements));
    }

    public YSequenceStatement(boolean hasBraces, ImmutableList<YStatement> statements) {
        this.statements = statements;
        this.hasBraces = hasBraces;
    }

    public ImmutableList<YStatement> getStatements() {
        return statements;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return getStatements().iterator();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YSequenceStatement copy() {
        return new YSequenceStatement(hasBraces, getStatements());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (hasBraces) {
            builder.append('{');
        }
        for (YStatement statement : getStatements()) {
            builder.append(statement.toString()).append(" ");
        }
        if (hasBraces) {
            builder.append('}');
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YSequenceStatement)) return false;
        YSequenceStatement that = (YSequenceStatement) o;
        return Objects.equals(getStatements(), that.getStatements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatements());
    }
}
