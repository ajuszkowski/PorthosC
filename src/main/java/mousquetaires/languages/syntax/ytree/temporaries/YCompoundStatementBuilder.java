package mousquetaires.languages.syntax.ytree.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;
import mousquetaires.utils.patterns.Builder;

import java.util.Iterator;


public class YCompoundStatementBuilder extends Builder<YStatement> implements YTempEntity {

    private boolean hasBraces;
    private final ImmutableList.Builder<YStatement> statements;
    private final ImmutableList.Builder<YJumpStatement> outerJumpStatements;

    public YCompoundStatementBuilder() {
        this(false);
    }

    public YCompoundStatementBuilder(boolean hasBraces) {
        this.hasBraces = hasBraces;
        this.statements = new ImmutableList.Builder<>();
        this.outerJumpStatements = new ImmutableList.Builder<>();
    }

    @Override
    public YCompoundStatement build() {
        // TODO: buildAndOptimise() ?
        return new YCompoundStatement(hasBraces, statements.build(), outerJumpStatements.build());
    }

    public void markHasBraces() {
        hasBraces = true;
    }

    // transforms single or zero statements into linear statement
    public YStatement buildAndOptimise() {
        ImmutableList<YJumpStatement> outerJumps = outerJumpStatements.build();
        int jumpsSize = outerJumps.size();
        ImmutableList<YStatement> statementsBuilt = statements.build();
        int length = statementsBuilt.size();
        if (length == 0) {
            assert jumpsSize == 0: jumpsSize;
            return YLinearStatement.createEmptyStatement();
        }
        if (length == 1) {
            assert jumpsSize == 0: jumpsSize;
            return statementsBuilt.get(0);
        }
        // TODO: add outer jumps to the result!
        return new YCompoundStatement(hasBraces, statementsBuilt, outerJumps);
    }

    public void addLinearStatement(YExpression expression) {
        add(new YLinearStatement(expression), false);
    }

    public void addStatement(YStatement statement) {
        if (statement instanceof YCompoundStatement) {
            YCompoundStatement sequenceStatement = (YCompoundStatement) statement;
            for (YStatement innerStatement : sequenceStatement.getStatements()) {
                add(innerStatement);
            }
        } else {
            add(statement);
        }
    }

    public void addAll(YCompoundStatementBuilder builder) {
        YCompoundStatement compoundStatement = builder.build();
        statements.addAll(compoundStatement.getStatements());
        outerJumpStatements.addAll(compoundStatement.getOuterJumpStatementsIterator());
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

    private void add(YStatement statement) {
        add(statement, true);
    }

    private void add(YStatement statement, boolean checkIfJump) {
        statements.add(statement);
        if (checkIfJump && statement instanceof YJumpStatement) {
            outerJumpStatements.add((YJumpStatement) statement);
        }
    }
}
