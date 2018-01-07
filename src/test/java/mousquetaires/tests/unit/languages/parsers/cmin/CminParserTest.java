package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.internalrepr.InternalSyntaxTree;
import mousquetaires.languages.internalrepr.expressions.InternalAssignmentExpression;
import mousquetaires.languages.internalrepr.expressions.InternalBinaryExpression;
import mousquetaires.languages.internalrepr.expressions.InternalConstant;
import mousquetaires.languages.internalrepr.expressions.InternalEqualityExpression;
import mousquetaires.languages.internalrepr.statements.InternalBranchingStatement;
import mousquetaires.languages.internalrepr.statements.InternalLinearStatement;
import mousquetaires.languages.internalrepr.types.InternalType;
import mousquetaires.languages.internalrepr.variables.InternalUntypedVariable;
import mousquetaires.languages.internalrepr.variables.InternalVariable;
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
        InternalBranchingStatement expected = new InternalBranchingStatement(
                new InternalEqualityExpression(
                        new InternalUntypedVariable("x"),
                        InternalConstant.newIntegerConstant(1)),
                new InternalLinearStatement(
                        new InternalAssignmentExpression(
                                new InternalUntypedVariable("y"),
                                InternalConstant.newIntegerConstant(2))),
                new InternalLinearStatement(
                        new InternalAssignmentExpression(
                                new InternalUntypedVariable("y"),
                                InternalConstant.newIntegerConstant(3))));
        InternalSyntaxTree internalRepr = runTest(structuresDirectory + "branchingStatement.c");
        assertEquals(1, internalRepr.getRoots().size());
        InternalBranchingStatement actualStatement = (InternalBranchingStatement) internalRepr.getRoots().get(0);
        assertEquals(expected, actualStatement);
    }
}
