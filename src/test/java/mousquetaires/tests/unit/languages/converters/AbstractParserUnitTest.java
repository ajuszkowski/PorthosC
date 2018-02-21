package mousquetaires.tests.unit.languages.converters;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.parsers.YtreeParser;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.tests.unit.AbstractUnitTest;
import mousquetaires.tests.TestFailedException;

import java.io.File;
import java.io.IOException;
import java.util.List;


public abstract class AbstractParserUnitTest extends AbstractUnitTest<YEntity> {

    protected final String parsersDirectory = resourcesDirectory + "converters/";

    @Override
    protected List<YEntity> parseTestFile(String testFile) {
        try {
            File file = new File(testFile);
            ProgramLanguage language = ProgramExtensions.parseProgramLanguage(file.getName());
            YSyntaxTree syntaxTree = YtreeParser.parse(file, language);
            return syntaxTree.getRoots();
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }
}
