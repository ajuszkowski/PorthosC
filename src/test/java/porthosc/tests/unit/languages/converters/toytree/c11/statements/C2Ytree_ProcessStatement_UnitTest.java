package porthosc.tests.unit.languages.converters.toytree.c11.statements;

import porthosc.languages.syntax.ytree.YEntity;
import porthosc.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import porthosc.languages.syntax.ytree.expressions.operations.YBinaryOperator;
import porthosc.languages.syntax.ytree.litmus.YPostludeDefinition;
import porthosc.languages.syntax.ytree.litmus.YProcessDefinition;
import porthosc.languages.syntax.ytree.statements.YCompoundStatement;
import porthosc.languages.syntax.ytree.statements.YLinearStatement;
import porthosc.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import porthosc.languages.syntax.ytree.types.YFunctionSignature;
import porthosc.languages.syntax.ytree.types.YMockType;
import porthosc.tests.unit.UnitTestPaths;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_ProcessStatement_UnitTest extends C2Ytree_Statement_UnitTest {

    @Test
    @Ignore("process statements syntax is temporarily not supported")
    public void test() {
        Iterator<? extends YEntity> expected = getIterator(
                new YProcessDefinition(origin,
                                       new YFunctionSignature("?", new YMockType()),// TODO: replace this mock signature with real
                                       new YCompoundStatement(origin,
                                                             true,
                                                             new YVariableDeclarationStatement(origin, typeInt, variableA),
                                                             new YLinearStatement(new YAssignmentExpression(origin, variableA, constant1)))),
                new YPostludeDefinition(origin, YBinaryOperator.Equals.createExpression(origin, variableA, constant2)));

        run(UnitTestPaths.c11StatementsDirectory + "processStatement.c", expected);
    }
}
