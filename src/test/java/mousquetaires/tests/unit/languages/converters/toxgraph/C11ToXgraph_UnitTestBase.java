package mousquetaires.tests.unit.languages.converters.toxgraph;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.Ytree2XgraphConverter;
import mousquetaires.languages.converters.toytree.YtreeParser;
import mousquetaires.languages.syntax.xgraph.XProgramBase;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.tests.TestFailedException;
import mousquetaires.tests.unit.Assertion;
import mousquetaires.tests.unit.languages.common.graph.AssertionXProcessesEqual;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class C11ToXgraph_UnitTestBase extends AbstractConverterUnitTest<XCyclicProcess> {

    protected abstract MemoryModel.Kind memoryModel();

    @Override
    protected Iterator<? extends XCyclicProcess> parseTestFile(String testFile) {
        try {
            DataModel dataModel = null; // TODO: consider data model also
            File file = new File(testFile);
            ProgramLanguage language = ProgramExtensions.parseProgramLanguage(file.getName());
            YtreeParser parser = new YtreeParser(file, language);
            YSyntaxTree internalRepr = parser.parseFile();
            Ytree2XgraphConverter converter = new Ytree2XgraphConverter(language, memoryModel(), dataModel);
            XProgramBase program = converter.convert(internalRepr);
            return program.getProcesses().iterator(); //TODO: check this warn 'Unchecked assignment'
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }

    @Override
    protected Assertion getComparingAssertion(XCyclicProcess expected, XCyclicProcess actual) {
        return new AssertionXProcessesEqual(expected, actual);
    }
}
