package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.ytree.InternalSyntaxTree;
import mousquetaires.languages.ytree.expressions.YAssignmentExpression;
import mousquetaires.languages.ytree.expressions.YEqualityExpression;
import mousquetaires.languages.ytree.expressions.YConstant;
import mousquetaires.languages.ytree.expressions.lvalue.YVariableRef;
import mousquetaires.languages.ytree.statements.YBranchingStatement;
import mousquetaires.languages.ytree.statements.YLinearStatement;
import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CminParserTest extends AbstractParserUnitTest {

    private final String rootDirectory = parsersDirectory + "cmin/";
    private final String structuresDirectory = rootDirectory + "structures/";

    @Test
    public void test_typedef() {
        InternalSyntaxTree internalRepr = runTest(structuresDirectory + "typedef.c");
        //assertEquals(programme)
    }

    @Test
    public void test_variableDeclarationStatement() {
        InternalSyntaxTree internalRepr = runTest(structuresDirectory + "variableDeclarationStatement.c");
        //assertEquals(programme)
    }

    @Test
    public void test_postfixExpression_call() {
        InternalSyntaxTree internalRepr = runTest(structuresDirectory + "postfixExpression_call.c");
        //assertEquals(programme)
    }

    @Test
    public void test_branchingStatement() {
        YBranchingStatement expected = new YBranchingStatement(
                new YEqualityExpression(
                        new YVariableRef("x"),
                        YConstant.newIntegerConstant(1)),
                new YLinearStatement(
                        new YAssignmentExpression(
                                new YVariableRef("y"),
                                YConstant.newIntegerConstant(2))),
                new YLinearStatement(
                        new YAssignmentExpression(
                                new YVariableRef("y"),
                                YConstant.newIntegerConstant(3))));
        InternalSyntaxTree internalRepr = runTest(structuresDirectory + "branchingStatement.c");
        assertEquals(1, internalRepr.getRoots().size());
        YBranchingStatement actualStatement = (YBranchingStatement) internalRepr.getRoots().get(0);
        assertEquals(expected, actualStatement);
    }
}
