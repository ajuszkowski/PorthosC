package porthosc.tests.unit.languages.converters.toytree.c11.statements;

import porthosc.languages.syntax.ytree.YEntity;
import porthosc.languages.syntax.ytree.definitions.YFunctionDefinition;
import porthosc.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import porthosc.languages.syntax.ytree.expressions.operations.YBinaryOperator;
import porthosc.languages.syntax.ytree.statements.YBranchingStatement;
import porthosc.languages.syntax.ytree.statements.YCompoundStatement;
import porthosc.languages.syntax.ytree.statements.YLinearStatement;
import porthosc.languages.syntax.ytree.types.YMockType;
import porthosc.languages.syntax.ytree.types.YFunctionSignature;
import porthosc.tests.unit.UnitTestPaths;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_BranchingStatement_UnitTest extends C2Ytree_Statement_UnitTest {

    @Test
    public void test() {
        Iterator<? extends YEntity> expected = getIterator(new YFunctionDefinition(origin,
                                                                                   new YFunctionSignature("?", new YMockType()),// TODO: replace this mock signature with real
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
