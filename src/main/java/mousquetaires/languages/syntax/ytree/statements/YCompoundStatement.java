package mousquetaires.languages.syntax.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Objects;


public class YCompoundStatement extends YStatement {

    // TODO: perhaps, we don't need the Compound statement without braces.
    private final boolean hasBraces; // defines whether sequence of statements has surrounding braces '{' '}'
    private final ImmutableList<YStatement> statements; // <- recursive

    public YCompoundStatement(CodeLocation location, YStatement... statements) {
        this(location, true, statements);
    }

    public YCompoundStatement(CodeLocation location, boolean hasBraces, YStatement... statements) {
        this(location, hasBraces, ImmutableList.copyOf(statements));
    }

    public YCompoundStatement(CodeLocation location, boolean hasBraces, ImmutableList<YStatement> statements) {
        this(location, "", hasBraces, statements);
    }

    private YCompoundStatement(CodeLocation location, String label, boolean hasBraces, ImmutableList<YStatement> statements) {
        super(location, label);
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
        return new YCompoundStatement(codeLocation(), newLabel, hasBraces, statements);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
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
