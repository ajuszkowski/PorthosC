package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.execution.Programme;
import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CminParserTest extends AbstractParserUnitTest {

    private final String rootDirectory = parsersDirectory + "cmin/";
    private final String structuresDirectory = rootDirectory + "structures/";

    @Test
    public void test_typedef() {
        Programme programme = runTest(structuresDirectory + "typedef.c");
        //assertEquals(programme)
    }
}
