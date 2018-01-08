package mousquetaires.languages.internalrepr.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.statements.InternalStatement;
import mousquetaires.languages.internalrepr.statements.InternalSequenceStatement;
import mousquetaires.patterns.Builder;


public class InternalSequenceStatementBuilder
        extends Builder<InternalSequenceStatement> implements InternalEntity {

    public final ImmutableList.Builder<InternalStatement> statements;

    public InternalSequenceStatementBuilder() {
        this.statements = new ImmutableList.Builder<>();
    }

    @Override
    public InternalSequenceStatement build() {
        return new InternalSequenceStatement(this);
    }

    public void append(InternalStatement statement) {
        statements.add(statement);
    }

    public ImmutableList<InternalStatement> getStatements() {
        return statements.build();
    }
}
