package porthosc.tests.unit.languages.converters.toytree;

import porthosc.languages.InputExtensions;
import porthosc.languages.InputLanguage;
import porthosc.languages.converters.InputParserBase;
import porthosc.languages.converters.toytree.YtreeParser;
import porthosc.languages.syntax.ytree.YEntity;
import porthosc.languages.syntax.ytree.YSyntaxTree;
import porthosc.tests.TestFailedException;
import porthosc.tests.unit.Assertion;
import porthosc.tests.unit.AssertionObjectsEqual;
import porthosc.tests.unit.languages.converters.AbstractConverterUnitTest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class C2Ytree_UnitTestBase extends AbstractConverterUnitTest<YEntity> {

    @Override
    protected Iterator<? extends YEntity> parseTestFile(String testFile) {
        try {
            File file = new File(testFile);
            InputLanguage language = InputExtensions.parseProgramLanguage(file.getName());
            InputParserBase parser = new YtreeParser(file, language);
            YSyntaxTree syntaxTree = parser.parseFile();
            return syntaxTree.getRoots().iterator();
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }

    @Override
    protected Assertion getComparingAssertion(YEntity expected, YEntity actual) {
        return new AssertionObjectsEqual(expected, actual);
    }
}
