package mousquetaires.languages.transformers.cmin.temp;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.ytree.statements.YBlockStatement;
import mousquetaires.languages.ytree.statements.YStatement;
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
        statements.add(statement);
        // todo: do this via visitor-cleaner in visitMain()
        //if (statement instanceof YMultiStatementTemp) {
        //    YMultiStatementTemp multiStatement = (YMultiStatementTemp) statement;
        //    for (YStatement innerStatement : multiStatement.statements) {
        //        statements.add(innerStatement);
        //    }
        //}
        //else {
        //    statements.add(statement);
        //}
    }
}
