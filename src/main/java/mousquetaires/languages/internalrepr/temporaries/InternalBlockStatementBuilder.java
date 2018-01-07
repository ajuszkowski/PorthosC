package mousquetaires.languages.internalrepr.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.statements.InternalBlockStatement;
import mousquetaires.languages.internalrepr.statements.InternalStatement;
import mousquetaires.patterns.Builder;


public class InternalBlockStatementBuilder
        extends Builder<InternalBlockStatement> implements InternalEntity {

    public final ImmutableList.Builder<InternalStatement> statements;

    public InternalBlockStatementBuilder() {
        this.statements = new ImmutableList.Builder<>();
    }

    @Override
    public InternalBlockStatement build() {
        return new InternalBlockStatement(this);
    }

    public void append(InternalStatement statement) {
        statements.add(statement);
    }

    public ImmutableList<InternalStatement> getStatements() {
        return statements.build();
    }
}
