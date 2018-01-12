package mousquetaires.languages.transformers.cmin.temp;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.ytree.statements.YStatement;
import mousquetaires.patterns.Builder;


public class YSequenceStatementBuilder extends Builder<YMultiStatementTemp> {

    public final ImmutableList.Builder<YStatement> statements;

    public YSequenceStatementBuilder() {
        this.statements = new ImmutableList.Builder<>();
    }

    @Override
    public YMultiStatementTemp build() {
        return new YMultiStatementTemp(statements.build());
    }

    public void add(YStatement statement) {
        statements.add(statement);
    }
}
