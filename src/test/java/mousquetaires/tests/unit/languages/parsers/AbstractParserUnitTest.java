package mousquetaires.tests.unit.languages.parsers;

import mousquetaires.execution.Programme;
import mousquetaires.languages.parsers.ProgrammeParser;
import mousquetaires.tests.AbstractTest;
import mousquetaires.tests.TestFailedException;

import java.io.File;
import java.io.IOException;

public abstract class AbstractParserUnitTest extends AbstractTest {

    protected final String parsersDirectory = resourcesDirectory + "parsers/";

    protected Programme runTest(String file) {
        Programme programme;
        try {
            programme = ProgrammeParser.parse(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
        return programme;
    }
}
