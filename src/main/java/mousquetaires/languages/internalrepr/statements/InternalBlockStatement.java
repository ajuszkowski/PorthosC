package mousquetaires.languages.internalrepr.statements;

import com.google.common.collect.ImmutableList;

import java.util.Objects;


public class InternalBlockStatement extends InternalStatement {

    public final ImmutableList<InternalStatement> statements;

    public InternalBlockStatement(ImmutableList<InternalStatement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (InternalStatement statement : statements) {
            builder.append(statement.toString()).append("; ");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalBlockStatement)) return false;
        InternalBlockStatement that = (InternalBlockStatement) o;
        return Objects.equals(statements, that.statements);
    }

    @Override
    public int hashCode() {

        return Objects.hash(statements);
    }
}
