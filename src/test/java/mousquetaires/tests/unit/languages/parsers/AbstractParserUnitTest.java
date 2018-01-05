package mousquetaires.tests.unit.languages.parsers;

import mousquetaires.languages.internalrepr.InternalSyntaxTree;
import mousquetaires.languages.parsers.InternalLanguageParser;
import mousquetaires.tests.AbstractTest;
import mousquetaires.tests.TestFailedException;

import java.io.File;
import java.io.IOException;


public abstract class AbstractParserUnitTest extends AbstractTest {

    protected final String parsersDirectory = resourcesDirectory + "parsers/";

    protected InternalSyntaxTree runTest(String file) {
        InternalSyntaxTree internalRepr;
        try {
            internalRepr = InternalLanguageParser.parse(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
        return internalRepr;
    }
}
