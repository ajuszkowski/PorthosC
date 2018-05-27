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
import mousquetaires.tests.unit.UnitTestPaths;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_BranchingStatement_UnitTest extends C2Ytree_Statement_UnitTest {

    @Test
    public void test() {
        Iterator<? extends YEntity> expected = getIterator(new YFunctionDefinition(origin,
                                                                                   new YMethodSignature("?", new YMockType()),// TODO: replace this mock signature with real
                                                                                   new YCompoundStatement(origin, true,
                                                                                                          new YBranchingStatement(
                                                                                                                  origin, YBinaryOperator.Equals.createExpression(
                                                                                                                  origin, variableX, constant1),
                                                                                                                  new YCompoundStatement(
                                                                                                                          origin, true,
                                                                                                                          new YLinearStatement(new YAssignmentExpression(
                                                                                                                                  origin, variableY, constant2)),
                                                                                                                          new YLinearStatement(new YAssignmentExpression(
                                                                                                                                  origin, variableX, variableY))                            ),
                                                                                                                  new YBranchingStatement(
                                                                                                                          origin, YBinaryOperator.Greater.createExpression(
                                                                                                                          origin, variableX, constant2),
                                                                                                                          new YCompoundStatement(
                                                                                                                                  origin, true, new YLinearStatement(new YAssignmentExpression(
                                                                                                                                  origin, variableY, constant3))),
                                                                                                                          new YCompoundStatement(
                                                                                                                                  origin, true))),
                                                                                                          new YLinearStatement(new YAssignmentExpression(
                                                                                                                  origin, variableX, constant4)))));
        run(UnitTestPaths.c11StatementsDirectory + "branchingStatement.c", expected);
    }
}
