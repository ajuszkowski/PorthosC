package mousquetaires.languages.cmin.transformers.tointernal.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.internalrepr.statements.YBlockStatement;
import mousquetaires.languages.internalrepr.statements.YStatement;
import mousquetaires.patterns.Builder;


public class YBlockStatementBuilder extends Builder<YBlockStatement> {

    public final ImmutableList.Builder<YStatement> statements;

    public YBlockStatementBuilder() {
        this.statements = new ImmutableList.Builder<>();
    }

    @Override
    public YBlockStatement build() {
        return new YBlockStatement(statements.build());
    }

    public void add(YStatement statement) {
        if (statement instanceof YTempMultiStatement) {
            YTempMultiStatement multiStatement = (YTempMultiStatement) statement;
            for (YStatement innerStatement : multiStatement.statements) {
                statements.add(innerStatement);
            }
        }
        else {
            statements.add(statement);
        }
    }
}
