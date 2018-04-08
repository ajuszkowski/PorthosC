package mousquetaires.languages.syntax.ytree.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.patterns.BuilderBase;

import java.util.Iterator;


public class YCompoundStatementBuilder extends BuilderBase<YCompoundStatement> implements YTempEntity {

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
        throw new UnsupportedOperationException();
    }

    @Override
    public <S> S accept(YtreeVisitor<S> visitor) {
        throw new UnsupportedOperationException();
    }
}
