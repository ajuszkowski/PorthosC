package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.litmus.YAssertionStatement;
import mousquetaires.languages.syntax.ytree.litmus.YProcessStatement;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;
import mousquetaires.tests.unit.UnitTestPaths;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_ProcessStatement_UnitTest extends C2Ytree_Statement_UnitTest {

    @Test
    @Ignore("process statements syntax is temporarily not supported")
    public void test() {
        Iterator<? extends YEntity> expected = getIterator(
                new YProcessStatement(new YMethodSignature("?", new YMockType(), new YParameter[0]),// TODO: replace this mock signature with real
                new YCompoundStatement(true,
                                       new YVariableDeclarationStatement(typeInt, variableA),
                                       new YLinearStatement(new YAssignmentExpression(variableA, constant1)))),
                new YAssertionStatement(YRelativeBinaryExpression.Kind.Equals.createExpression(variableA, constant2)));
        run(UnitTestPaths.c11StatementsDirectory + "processStatement.c", expected);
    }
}
