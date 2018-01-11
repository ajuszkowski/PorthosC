package mousquetaires.languages.internalrepr.statements;

import com.google.common.collect.ImmutableList;

import java.util.Objects;


public class YBlockStatement extends YStatement {

    public final ImmutableList<YStatement> statements;

    public YBlockStatement(YStatement... statements) {
        this.statements = ImmutableList.copyOf(statements);
    }

    public YBlockStatement(ImmutableList<YStatement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder('{');
        for (YStatement statement : statements) {
            builder.append(statement.toString()).append(" ");
        }
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YBlockStatement)) return false;
        YBlockStatement that = (YBlockStatement) o;
        return Objects.equals(statements, that.statements);
    }

    @Override
    public int hashCode() {

        return Objects.hash(statements);
    }
}
