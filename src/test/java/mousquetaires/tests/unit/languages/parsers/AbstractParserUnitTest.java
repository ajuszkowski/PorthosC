package mousquetaires.tests.unit.languages.parsers;

import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.parsers.YtreeParser;
import mousquetaires.tests.AbstractTest;
import mousquetaires.tests.TestFailedException;

import java.io.File;
import java.io.IOException;


public abstract class AbstractParserUnitTest extends AbstractTest {

    protected final String parsersDirectory = resourcesDirectory + "parsers/";

    protected YSyntaxTree runTest(String file) {
        YSyntaxTree internalRepr;
        try {
            internalRepr = YtreeParser.parse(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
        return internalRepr;
    }
}
