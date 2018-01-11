package mousquetaires.languages.internalrepr.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.cmin.transformers.tointernal.temporaries.YBlockStatementBuilder;

import java.util.Objects;


public class YBlockStatement extends YStatement {

    public final ImmutableList<YStatement> statements;
    public final boolean switchContext; // if true, has braces '{' '}' around

    public YBlockStatement(YStatement... statements) {
        this.statements = ImmutableList.copyOf(statements);
        this.switchContext = false;
    }

    public YBlockStatement(boolean switchContext, YStatement... statements) {
        this.statements = ImmutableList.copyOf(statements);
        this.switchContext = switchContext;
    }

    public YBlockStatement(boolean switchContext, ImmutableList<YStatement> statements) {
        this.statements = statements;
        this.switchContext = switchContext;
    }

    public YBlockStatement(YBlockStatementBuilder builder) {
        this.statements = builder.getStatements();
        this.switchContext = builder.getSwitchContext();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (switchContext) {
            builder.append("{ ");
        }
        for (YStatement statement : statements) {
            builder.append(statement.toString()).append(" ");
        }
        if (switchContext) {
            builder.append(" }");
        }
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
