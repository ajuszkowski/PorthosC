package mousquetaires.tests.unit.languages.converters.toxgraph;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.YtreeToXgraphConverter;
import mousquetaires.languages.converters.toytree.YtreeParser;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.tests.TestFailedException;
import mousquetaires.tests.unit.FlowGraphComparer;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class C11ToXgraph_UnitTestBase extends AbstractConverterUnitTest<XFlowGraph> {

    @Override
    protected Iterator<? extends XFlowGraph> parseTestFile(String testFile) {
        try {
            DataModel dataModel = null; // TODO: consider data model also
            File file = new File(testFile);
            ProgramLanguage language = ProgramExtensions.parseProgramLanguage(file.getName());
            YSyntaxTree internalRepr = YtreeParser.parse(file, language);
            YtreeToXgraphConverter converter = new YtreeToXgraphConverter(language, dataModel);
            XProgram program = converter.convert(internalRepr);
            return program.getAllProcesses().iterator();
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }

    @Override
    protected void assertObjectsEqual(XFlowGraph expected, XFlowGraph actual) {
        Assert.assertEquals("process ID mismatch", expected.processId(), actual.processId());
        FlowGraphComparer.assertGraphsEqual(expected, actual);
    }
}
