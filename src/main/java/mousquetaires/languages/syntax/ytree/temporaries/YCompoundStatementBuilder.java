package mousquetaires.languages.syntax.ytree.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;
import mousquetaires.utils.patterns.Builder;

import java.util.Iterator;


public class YCompoundStatementBuilder extends Builder<YCompoundStatement> implements YTempEntity {

    private boolean hasBraces;
    private YTempListBuilder<YStatement> statementsBuilder;

    public YCompoundStatementBuilder() {
        this.statementsBuilder = new YTempListBuilder<>();
    }

    @Override
    public YCompoundStatement build() {
        // TODO: buildAndOptimise() ?
        return new YCompoundStatement(hasBraces, statementsBuilder.build());
    }

    public void markHasBraces() {
        hasBraces = true;
    }

    // transforms single or zero statementsBuilder into linear statement
    public YStatement buildOptimised() {
        ImmutableList<YStatement> statements = statementsBuilder.build();
        int length = statements.size();
        if (length == 0) {
            return YLinearStatement.createEmptyStatement();
        }
        if (length == 1) {
            return statements.get(0);
        }
        return new YCompoundStatement(hasBraces, statements);
    }

    public void addLinearStatement(YExpression expression) {
        statementsBuilder.add(new YLinearStatement(expression));
    }

    public void addStatement(YStatement statement) {
        if (statement instanceof YCompoundStatement) {
            YCompoundStatement sequenceStatement = (YCompoundStatement) statement;
            for (YStatement innerStatement : sequenceStatement.getStatements()) {
                statementsBuilder.add(innerStatement);
            }
        } else {
            statementsBuilder.add(statement);
        }
    }

    public void addFrom(YCompoundStatementBuilder builder) {
        YCompoundStatement compoundStatement = builder.build();
        statementsBuilder.addAll(compoundStatement.getStatements());
    }



    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new NotSupportedException();
    }

    @Override
    public <S> S accept(YtreeVisitor<S> visitor) {
        throw new NotSupportedException();
    }

    @Override
    public YEntity copy() {
        throw new NotSupportedException();
    }
}
