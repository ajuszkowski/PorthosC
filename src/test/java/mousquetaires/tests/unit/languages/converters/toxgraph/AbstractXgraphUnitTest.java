package mousquetaires.tests.unit.languages.converters.toxgraph;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.YtreeToXgraphConverter;
import mousquetaires.languages.parsers.YtreeParser;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.tests.TestFailedException;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;
import mousquetaires.utils.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class AbstractXgraphUnitTest extends AbstractConverterUnitTest<XEntity> {

    @Override
    protected Iterator<? extends XEntity> parseTestFile(String testFile) {
        try {
            DataModel dataModel = null; // TODO: consider data model also
            File file = new File(testFile);
            ProgramLanguage language = ProgramExtensions.parseProgramLanguage(file.getName());
            YSyntaxTree internalRepr = YtreeParser.parse(file, language);
            YtreeToXgraphConverter converter = new YtreeToXgraphConverter(language, dataModel);
            XProgram program = converter.convert(internalRepr);
            return getIterator(program);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }

    protected Iterator<? extends XEntity> getIterator(XProcess process) {
        return CollectionUtils.createIteratorFrom(process);
    }

    protected Iterator<? extends XEntity> getIterator(XProgram program) {
        return program.getAllProcesses().iterator();
        //return new XgraphLinearisedTraverser(program);
    }


}
