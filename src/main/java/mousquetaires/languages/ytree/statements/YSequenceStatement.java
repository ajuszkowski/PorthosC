package mousquetaires.languages.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.cmin.transformer.temporaries.YSequenceStatementBuilder;

import java.util.Objects;


public class YSequenceStatement extends YStatement {
    public final ImmutableList<YStatement> statements;

    public YSequenceStatement(YSequenceStatementBuilder builder) {
        this.statements = builder.getStatements();
    }

    // adds block brackets '{' '}' around the statement sequence
    public YBlockStatement toBlockStatement() {
        return new YBlockStatement(statements);
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
