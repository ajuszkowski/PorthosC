package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.statements.YBranchingStatement;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.tests.unit.UnitTestPaths;
import org.junit.Test;

import java.util.Iterator;


public class C11ParseBranchingStatementTest extends C11ParseStatementTest {

    @Test
    public void test_branchingStatement() {
        Iterator<? extends YEntity> expected = getIterator(new YFunctionDefinition(
                new YCompoundStatement(true, new YBranchingStatement(
                        YRelativeBinaryExpression.Kind.Equals.createExpression(variableX, constant1),
                        new YCompoundStatement(true, new YLinearStatement(new YAssignmentExpression(variableY, constant2))),
                        new YCompoundStatement(true, new YLinearStatement(new YAssignmentExpression(variableY, constant3)))))));
        run(UnitTestPaths.c11StatementsDirectory + "branchingStatement.c", expected);
    }
}
