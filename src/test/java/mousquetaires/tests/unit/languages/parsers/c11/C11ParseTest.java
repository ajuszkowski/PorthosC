package mousquetaires.tests.unit.languages.parsers.c11;

import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;


public abstract class C11ParseTest extends AbstractParserUnitTest {

    protected final String rootDirectory = parsersDirectory + "c11/";

    //protected void runParserTest(String file, YSyntaxTree expectedTree) {
    //    YSyntaxTree actualTree = runTest(file);
    //    List<YEntity> actualRoots = actualTree.getRoots();
    //    List<YEntity> expectedRoots = expectedTree.getRoots();
    //    assertEquals("roots number does not match", expectedRoots.size(), actualRoots.size());
    //    for (int i = 0; i < expectedRoots.size(); ++i) {
    //        assertEquals("mismatch in " + i + "th statement:", expectedRoots.get(i), actualRoots.get(i));
    //    }
    //}

}
