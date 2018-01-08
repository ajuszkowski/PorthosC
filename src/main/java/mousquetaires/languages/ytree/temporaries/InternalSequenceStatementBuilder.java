package mousquetaires.languages.ytree.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.statements.YStatement;
import mousquetaires.languages.ytree.statements.YSequenceStatement;
import mousquetaires.patterns.Builder;


public class InternalSequenceStatementBuilder
        extends Builder<YSequenceStatement> implements YEntity {

    public final ImmutableList.Builder<YStatement> statements;

    public InternalSequenceStatementBuilder() {
        this.statements = new ImmutableList.Builder<>();
    }

    @Override
    public YSequenceStatement build() {
        return new YSequenceStatement(this);
    }

    public void append(YStatement statement) {
        statements.add(statement);
    }

    public ImmutableList<YStatement> getStatements() {
        return statements.build();
    }
}
