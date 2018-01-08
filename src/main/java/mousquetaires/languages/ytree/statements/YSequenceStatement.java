package mousquetaires.languages.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.ytree.temporaries.InternalSequenceStatementBuilder;


public class YSequenceStatement extends YStatement {
    public final ImmutableList<YStatement> statements;

    public YSequenceStatement(InternalSequenceStatementBuilder builder) {
        this.statements = builder.getStatements();
    }

    // adds block brackets '{' '}' around the statement sequence
    public YBlockStatement toBlockStatement() {
        return new YBlockStatement(statements);
    }
}
