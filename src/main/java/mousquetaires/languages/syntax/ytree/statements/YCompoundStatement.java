package mousquetaires.languages.syntax.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.ytree.YtreeVisitor;

import java.util.Iterator;
import java.util.Objects;


public class YCompoundStatement extends YStatement {

    private final boolean hasBraces; // defines whether sequence of statements has surrounding braces '{' '}'
    private final ImmutableList<YStatement> statements; // <- recursive

    public YCompoundStatement(YStatement... statements) {
        this(true, statements);
    }

    public YCompoundStatement(boolean hasBraces, YStatement... statements) {
        this(hasBraces, ImmutableList.copyOf(statements));
    }

    public YCompoundStatement(boolean hasBraces, ImmutableList<YStatement> statements) {
        this("", hasBraces, statements);
    }

    private YCompoundStatement(String label, boolean hasBraces, ImmutableList<YStatement> statements) {
        super(label);
        this.statements = statements;
        this.hasBraces = hasBraces;
    }

    public ImmutableList<YStatement> getStatements() {
        return statements;
    }

    public boolean hasBraces() {
        return hasBraces;
    }

    @Override
    public YCompoundStatement withLabel(String newLabel) {
        return new YCompoundStatement(newLabel, hasBraces, statements);
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
    public YCompoundStatement copy() {
        return new YCompoundStatement(hasBraces(), getStatements());
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
        if (!(o instanceof YCompoundStatement)) return false;
        YCompoundStatement that = (YCompoundStatement) o;
        return hasBraces == that.hasBraces &&
                Objects.equals(getStatements(), that.getStatements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasBraces, getStatements());
    }
}
