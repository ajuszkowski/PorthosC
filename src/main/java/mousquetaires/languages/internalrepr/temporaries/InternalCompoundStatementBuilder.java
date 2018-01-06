package mousquetaires.languages.internalrepr.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.statements.InternalCompoundStatement;
import mousquetaires.languages.internalrepr.statements.InternalStatement;
import mousquetaires.patterns.Builder;


public class InternalCompoundStatementBuilder
        extends Builder<InternalCompoundStatement> implements InternalEntity {

    public final ImmutableList.Builder<InternalStatement> statements;

    public InternalCompoundStatementBuilder() {
        this.statements = new ImmutableList.Builder<>();
    }

    @Override
    public InternalCompoundStatement build() {
        return new InternalCompoundStatement(this);
    }

    public void addStatement(InternalStatement statement) {
        statements.add(statement);
    }

    public ImmutableList<InternalStatement> getStatements() {
        return statements.build();
    }
}
