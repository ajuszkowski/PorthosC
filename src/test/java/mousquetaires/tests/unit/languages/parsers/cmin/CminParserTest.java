package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.YAssignmentExpression;
import mousquetaires.languages.ytree.expressions.YEqualityExpression;
import mousquetaires.languages.ytree.expressions.YConstant;
import mousquetaires.languages.ytree.expressions.YVariableRef;
import mousquetaires.languages.ytree.statements.YBlockStatement;
import mousquetaires.languages.ytree.statements.YBranchingStatement;
import mousquetaires.languages.ytree.statements.YLinearStatement;
import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public abstract class CminParserTest extends AbstractParserUnitTest {

    protected final String rootDirectory = parsersDirectory + "cmin/";
    protected final String structuresDirectory = rootDirectory + "structures/";


    @Test
    public void test_variableDeclarationStatement() {
        YSyntaxTree parsed = runTest(structuresDirectory + "variableDeclarationStatement.c");
        //assertEquals(programme)
    }

    @Test
    public void test_postfixExpression_call() {
        YSyntaxTree parsed = runTest(structuresDirectory + "postfixExpression_call.c");
        //assertEquals(programme)
    }

    @Test
    public void test_branchingStatement() {
        YBranchingStatement expected = new YBranchingStatement(
                new YEqualityExpression(
                        YVariableRef.create("x"),
                        YConstant.createInteger(1)),
                new YBlockStatement(
                        new YLinearStatement(
                                new YAssignmentExpression(
                                        YVariableRef.create("y"),
                                        YConstant.createInteger(2)))),
                new YBlockStatement(
                        new YLinearStatement(
                            new YAssignmentExpression(
                                YVariableRef.create("y"),
                                YConstant.createInteger(3)))));
        YSyntaxTree parsed = runTest(structuresDirectory + "branchingStatement.c");
        assertEquals(1, parsed.getRoots().size());
        YBranchingStatement actualStatement = (YBranchingStatement) parsed.getRoots().get(0);
        assertEquals(expected, actualStatement);
    }
}
