package mousquetaires.tests.unit.languages.converters;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.YtreeToXgraphConverter;
import mousquetaires.languages.parsers.YtreeParser;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.tests.AbstractTest;
import mousquetaires.tests.TestFailedException;

import java.io.File;
import java.io.IOException;


public abstract class AbstractConverterUnitTest extends AbstractTest {

    protected final String convertersDirectory = resourcesDirectory + "converters/";

    protected XProgram runTest(String filePath) {
        YSyntaxTree internalRepr;
        try {
            DataModel dataModel = null; // TODO: consider data model also
            File file = new File(filePath);
            ProgramLanguage language = ProgramExtensions.parseProgramLanguage(file.getName());
            internalRepr = YtreeParser.parse(file, language);
            YtreeToXgraphConverter converter = new YtreeToXgraphConverter(language, dataModel);
            return converter.convert(internalRepr);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }
}
