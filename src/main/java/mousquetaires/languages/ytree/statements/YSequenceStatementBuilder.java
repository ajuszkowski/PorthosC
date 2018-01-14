package mousquetaires.languages.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.patterns.Builder;


public class YSequenceStatementBuilder extends Builder<YStatement> {

    public final ImmutableList.Builder<YStatement> statements;

    public YSequenceStatementBuilder() {
        this.statements = new ImmutableList.Builder<>();
    }

    @Override
    public YStatement build() {
        ImmutableList<YStatement> statementsBuilt = this.statements.build();
        int length = statementsBuilt.size();
        if (length == 0) {
            return YLinearStatement.emptyStatement();
        }
        if (length == 1) {
            return statementsBuilt.get(0);
        }
        return new YSequenceStatement(statementsBuilt);
    }

    public void add(YStatement statement) {
        statements.add(statement);
    }
}
