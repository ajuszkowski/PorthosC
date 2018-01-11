package mousquetaires.languages.cmin.transformers.tointernal.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.internalrepr.statements.YStatement;
import mousquetaires.patterns.Builder;


public class YSequenceStatementBuilder extends Builder<YTempMultiStatement> {

    public final ImmutableList.Builder<YStatement> statements;

    public YSequenceStatementBuilder() {
        this.statements = new ImmutableList.Builder<>();
    }

    @Override
    public YTempMultiStatement build() {
        return new YTempMultiStatement(statements.build());
    }

    public void add(YStatement statement) {
        statements.add(statement);
    }
}
