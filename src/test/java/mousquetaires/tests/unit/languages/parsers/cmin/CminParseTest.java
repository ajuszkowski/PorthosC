package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;

import java.util.List;

import static org.junit.Assert.assertEquals;


public abstract class CminParseTest extends AbstractParserUnitTest {

    protected final String rootDirectory = parsersDirectory + "cmin/";

    protected void runParserTest(String file, YSyntaxTree expectedTree) {
        YSyntaxTree actualTree = runTest(file);
        List<YStatement> actualRoots = actualTree.getRoots();
        List<YStatement> expectedRoots = expectedTree.getRoots();
        assertEquals("roots number does not match", expectedRoots.size(), actualRoots.size());
        for (int i = 0; i < expectedRoots.size(); ++i) {
            assertEquals("mismatch in " + i + "th statement:", expectedRoots.get(i), actualRoots.get(i));
        }
    }

}
