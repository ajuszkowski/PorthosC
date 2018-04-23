package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.operations.YBinaryOperator;
import mousquetaires.languages.syntax.ytree.statements.YBranchingStatement;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;
import mousquetaires.tests.unit.UnitTestPaths;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_BranchingStatement_UnitTest extends C2Ytree_Statement_UnitTest {

    @Test
    public void test() {
        Iterator<? extends YEntity> expected = getIterator(new YFunctionDefinition(location,
                new YMethodSignature("?", new YMockType()),// TODO: replace this mock signature with real
                new YCompoundStatement(location, true,
                        new YBranchingStatement(location, YBinaryOperator.Equals.createExpression(location, variableX, constant1),
                                new YCompoundStatement(location, true,
                                    new YLinearStatement(new YAssignmentExpression(location, variableY, constant2)),
                                    new YLinearStatement(new YAssignmentExpression(location, variableX, variableY))                            ),
                                new YBranchingStatement(location, YBinaryOperator.Greater.createExpression(location, variableX, constant2),
                                    new YCompoundStatement(location, true, new YLinearStatement(new YAssignmentExpression(location, variableY, constant3))),
                                    new YCompoundStatement(location, true))),
                        new YLinearStatement(new YAssignmentExpression(location, variableX, constant4)))));
        run(UnitTestPaths.c11StatementsDirectory + "branchingStatement.c", expected);
    }
}
