package mousquetaires.tests.unit.languages.parsers.cmin.statements;

import mousquetaires.languages.internalrepr.YSyntaxTree;
import mousquetaires.languages.internalrepr.expressions.YAssignmentExpression;
import mousquetaires.languages.internalrepr.expressions.YEqualityExpression;
import mousquetaires.languages.internalrepr.statements.YBlockStatement;
import mousquetaires.languages.internalrepr.statements.YLinearStatement;
import mousquetaires.languages.internalrepr.statements.YVariableDeclarationStatement;
import mousquetaires.languages.internalrepr.statements.artificial.YBugonStatement;
import mousquetaires.languages.internalrepr.statements.artificial.YProcess;
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
