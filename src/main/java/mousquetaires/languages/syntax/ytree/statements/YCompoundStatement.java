package mousquetaires.languages.syntax.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;

import java.util.Iterator;
import java.util.Objects;


public class YCompoundStatement extends YStatement {

    private final boolean hasBraces; // defines whether sequence of statements has surrounding braces '{' '}'
    private final ImmutableList<YStatement> statements; // <- recursive
    private final ImmutableList<YJumpStatement> outerJumpStatements;

    public YCompoundStatement(boolean hasBraces, YStatement... statements) {
        this(hasBraces, ImmutableList.copyOf(statements), ImmutableList.of());
    }

    public YCompoundStatement(boolean hasBraces,
                              ImmutableList<YStatement> statements,
                              ImmutableList<YJumpStatement> outerJumpStatements) {
        this(newLabel(), hasBraces, statements, outerJumpStatements);
    }

    private YCompoundStatement(String label,
                               boolean hasBraces,
                               ImmutableList<YStatement> statements,
                               ImmutableList<YJumpStatement> outerJumpStatements) {
        super(label);
        this.statements = statements;
        this.hasBraces = hasBraces;
        this.outerJumpStatements = outerJumpStatements;
    }

    public ImmutableList<YStatement> getStatements() {
        return statements;
    }

    public boolean hasBraces() {
        return hasBraces;
    }

    public Iterator<YJumpStatement> getOuterJumpStatementsIterator() {
        return outerJumpStatements.iterator();
    }

    @Override
    public YCompoundStatement withLabel(String newLabel) {
        return new YCompoundStatement(newLabel, hasBraces, statements, outerJumpStatements);
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
        return new YCompoundStatement(hasBraces(), getStatements(), outerJumpStatements);
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
        return Objects.equals(getStatements(), that.getStatements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatements());
    }
}
