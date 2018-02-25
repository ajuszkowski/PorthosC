package mousquetaires.tests.unit.languages.converters.toytree;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.parsers.YtreeParser;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.tests.TestFailedException;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class AbstractYtreeUnitTest extends AbstractConverterUnitTest<YEntity> {

    @Override
    protected Iterator<? extends YEntity> parseTestFile(String testFile) {
        try {
            File file = new File(testFile);
            ProgramLanguage language = ProgramExtensions.parseProgramLanguage(file.getName());
            YSyntaxTree syntaxTree = YtreeParser.parse(file, language);
            return syntaxTree.getRoots().iterator();
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }

    @Override
    protected void assertObjectsEqual(YEntity expected, YEntity actual) {
        Assert.assertEquals(expected, actual);
    }
}
