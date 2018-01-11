package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;

import java.util.List;

import static org.junit.Assert.assertEquals;


public abstract class CminParseTest extends AbstractParserUnitTest {

    protected final String rootDirectory = parsersDirectory + "cmin/";

    protected void runParserTest(String file, YSyntaxTree expectedTree) {
        YSyntaxTree actualTree = runTest(file);
        List<YEntity> actualRoots = actualTree.getRoots();
        List<YEntity> expectedRoots = expectedTree.getRoots();
        assertEquals("roots number does not match", expectedRoots.size(), actualRoots.size());
        for (int i = 0; i < expectedRoots.size(); ++i) {
            assertEquals("mismatch in " + i + "th statement:", expectedRoots.get(i), actualRoots.get(i));
        }
    }

}
