package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.execution.Programme;
import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;
import org.junit.Test;


public class CminParserTest extends AbstractParserUnitTest {

    private final String rootDirectory = parsersDirectory + "cmin/";
    private final String structuresDirectory = rootDirectory + "structures/";

    @Test
    public void test_typedef() {
        Programme programme = runTest(structuresDirectory + "typedef.c");
        //assertEquals(programme)
    }

    @Test
    public void test_variableDeclarationStatement() {
        Programme programme = runTest(structuresDirectory + "variableDeclarationStatement.c");
        //assertEquals(programme)
    }
}
