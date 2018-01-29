package mousquetaires.tests.unit.languages.parsers.c11.statements;

import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YBranchingStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import org.junit.Test;


public class C11ParseBranchingStatementTest extends C11ParseStatementTest {

    @Test
    public void test_branchingStatement() {
        YSyntaxTree expected = new YSyntaxTree(
                new YBranchingStatement(
                        YRelativeBinaryExpression.Kind.Equals.createExpression(variableX, constant1),
                        new YCompoundStatement(true, new YLinearStatement(new YAssignmentExpression(variableY, constant2))),
                        new YCompoundStatement(true, new YLinearStatement(new YAssignmentExpression(variableY, constant3)))));
        runParserTest(statementsDirectory + "branchingStatement.c", expected);
    }
}
