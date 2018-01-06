package mousquetaires.languages.internalrepr.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.temporaries.InternalCompoundStatementBuilder;


public class InternalCompoundStatement implements InternalEntity {
    public final ImmutableList<InternalStatement> statements;

    public InternalCompoundStatement(InternalCompoundStatementBuilder builder) {
        this.statements = builder.getStatements();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (InternalStatement statement : statements) {
            builder.append(statement.toString()).append("; ");
        }
        return builder.toString();
    }
}
