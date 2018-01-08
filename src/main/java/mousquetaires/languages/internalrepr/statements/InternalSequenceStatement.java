package mousquetaires.languages.internalrepr.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.internalrepr.temporaries.InternalSequenceStatementBuilder;


public class InternalSequenceStatement extends InternalStatement {
    public final ImmutableList<InternalStatement> statements;

    public InternalSequenceStatement(InternalSequenceStatementBuilder builder) {
        this.statements = builder.getStatements();
    }

    // adds block brackets '{' '}' around the statement sequence
    public InternalBlockStatement toBlockStatement() {
        return new InternalBlockStatement(statements);
    }
}
