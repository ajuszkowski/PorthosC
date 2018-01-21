package mousquetaires.tests.unit.languages.parsers.cmin.statements;

import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.statements.YSequenceStatement;
import mousquetaires.languages.syntax.ytree.statements.labeled.YBranchingStatement;
import mousquetaires.languages.syntax.ytree.statements.labeled.YLinearStatement;
import org.junit.Test;


public class CminParseBranchingStatementTest extends CminParseStatementTest {

    @Test
    public void test_branchingStatement() {
        YSyntaxTree expected = new YSyntaxTree(
                new YBranchingStatement(
                        YRelativeBinaryExpression.Kind.Equals.createExpression(variableX, constant1),
                        new YSequenceStatement(true, new YLinearStatement(new YAssignmentExpression(variableY, constant2))),
                        new YSequenceStatement(true, new YLinearStatement(new YAssignmentExpression(variableY, constant3)))));
        runParserTest(statementsDirectory + "branchingStatement.c", expected);
    }
}
