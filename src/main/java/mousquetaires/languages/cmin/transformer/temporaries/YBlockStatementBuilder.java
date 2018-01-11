package mousquetaires.languages.cmin.transformer.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.statements.YStatement;
import mousquetaires.languages.ytree.statements.YBlockStatement;
import mousquetaires.patterns.Builder;


public class YBlockStatementBuilder extends Builder<YStatement> implements YEntity {

    public final ImmutableList.Builder<YStatement> statements;
    public boolean switchContext; // if true, has braces '{' '}' around

    public YBlockStatementBuilder() {
        this(false);
    }

    public YBlockStatementBuilder(boolean switchContext) {
        this.statements = new ImmutableList.Builder<>();
        this.switchContext = switchContext;
    }

    @Override
    public YStatement build() {
        ImmutableList<YStatement> statementsList = statements.build();
        if (statementsList.size() > 1) {
            return new YBlockStatement(switchContext, statementsList);
        }
        return statementsList.get(0);
    }

    public void setSwitchContext() {
        switchContext = true;
    }

    public boolean getSwitchContext() {
        return switchContext;
    }

    public void add(YStatement statement) {
        statements.add(statement);
    }

    public ImmutableList<YStatement> getStatements() {
        return statements.build();
    }
}
