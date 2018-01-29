package mousquetaires.tests.unit.languages.parsers.c11.statements;

import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.specific.YAssertionStatement;
import mousquetaires.languages.syntax.ytree.specific.YProcessStatement;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import org.junit.Test;


public class C11ParseProcessStatementTest extends C11ParseStatementTest {

    @Test
    public void test_processStatement() {
        YSyntaxTree expected = new YSyntaxTree(
                new YProcessStatement(1, new YCompoundStatement(true,
                        new YVariableDeclarationStatement(typeInt, variableA),
                        new YLinearStatement(new YAssignmentExpression(variableA, constant1)))),
                new YAssertionStatement(
                        YRelativeBinaryExpression.Kind.Equals.createExpression(variableA, constant2)));
        runParserTest(statementsDirectory + "processStatement.c", expected);
    }
}
