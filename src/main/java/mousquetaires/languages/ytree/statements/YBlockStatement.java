package mousquetaires.languages.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.cmin.transformer.temporaries.YBlockStatementBuilder;

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

    public YBlockStatement(YBlockStatementBuilder builder) {
        this.statements = builder.getStatements();
        this.switchContext = builder.getSwitchContext();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (switchContext) {
            sb.append("{ ");
        }
        for (YStatement statement : statements) {
            sb.append(statement.toString()).append(" ");
        }
        if (switchContext) {
            sb.append(" }");
        }
        return sb.toString();
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
