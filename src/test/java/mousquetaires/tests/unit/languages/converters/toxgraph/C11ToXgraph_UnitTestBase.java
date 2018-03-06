package mousquetaires.tests.unit.languages.converters.toxgraph;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.YtreeToXgraphConverter;
import mousquetaires.languages.converters.toytree.YtreeParser;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.tests.TestFailedException;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class C11ToXgraph_UnitTestBase extends AbstractConverterUnitTest<XProcess> {

    @Override
    protected Iterator<? extends XProcess> parseTestFile(String testFile) {
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
    protected void assertObjectsEqual(XProcess expected, XProcess actual) {
        Assert.assertEquals("processes ID mismatch", expected.processId, actual.processId);
        Assert.assertEquals("entry events do not match", expected.entry, actual.entry);
        Assert.assertEquals("exit events do not match", expected.exit, actual.exit);
        assertMapsEqual("epsilonJumps mismatch", expected.epsilonJumps, actual.epsilonJumps);
        assertMapsEqual("condTrueJumps mismatch", expected.condTrueJumps, actual.condTrueJumps);
        assertMapsEqual("condFalseJumps mismatch", expected.condFalseJumps, actual.condFalseJumps);
    }


}
