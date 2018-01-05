package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.internalrepr.InternalSyntaxTree;
import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;
import org.junit.Test;


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
}
