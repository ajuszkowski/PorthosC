package mousquetaires.languages.transformers.cmin.temp;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.ytree.statements.YStatement;

public class YTempMultiStatement extends YStatement {

    public final ImmutableList<YStatement> statements;

    public YTempMultiStatement(ImmutableList<YStatement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (YStatement statement : statements) {
            builder.append(statement.toString()).append(" ");
        }
        return builder.toString();
    }
}
