package mousquetaires.tests.unit.languages.parsers.cmin.statements;

import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.expressions.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.YEqualityExpression;
import mousquetaires.languages.syntax.ytree.statements.YBlockStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.syntax.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.syntax.ytree.statements.artificial.YProcess;
import org.junit.Test;


public class CminParseProcessStatementTest extends CminParseStatementTest {

    @Test
    public void test_processStatement() {
        YSyntaxTree expected = new YSyntaxTree(
                new YProcess("pX", new YBlockStatement(
                        new YVariableDeclarationStatement(typeInt, variableA),
                        new YLinearStatement(new YAssignmentExpression(variableA, constant1)))),
                new YBugonStatement(
                        new YEqualityExpression(variableA, constant2)));
        runParserTest(statementsDirectory + "processStatement.c", expected);
    }
}
