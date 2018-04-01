package mousquetaires.tests.unit.languages.converters.tozformula;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.YtreeToXgraphConverter;
import mousquetaires.languages.converters.toytree.YtreeParser;
import mousquetaires.languages.converters.tozformula.XProgramToZformulaConverter;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.zformula.ZLogicalFormula;
import mousquetaires.languages.transformers.xgraph.XProgramTransformer;
import mousquetaires.tests.TestFailedException;
import mousquetaires.tests.unit.Assertion;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class C11ToZformula_UnitTestBase extends AbstractConverterUnitTest<ZLogicalFormula> {

    @Override
    protected Iterator<? extends ZLogicalFormula> parseTestFile(String testFile) {
        try {
            DataModel dataModel = null; // TODO: consider data model also
            int unrollBound = 6; // TODO: PASS IT AS TEST PARAMETER (object testSettings)
            File file = new File(testFile);
            ProgramLanguage language = ProgramExtensions.parseProgramLanguage(file.getName());
            YSyntaxTree internalRepr = YtreeParser.parse(file, language);
            YtreeToXgraphConverter converter = new YtreeToXgraphConverter(language, dataModel);
            XProgram program = converter.convert(internalRepr);
            XUnrolledProgram unrolledProgram = XProgramTransformer.unroll(program, unrollBound);
            XProgramToZformulaConverter encoder = new XProgramToZformulaConverter();
            ZLogicalFormula smtFormula = encoder.encode(unrolledProgram);
            return CollectionUtils.createIteratorFrom(smtFormula);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }

    @Override
    protected Assertion getComparingAssertion(ZLogicalFormula expected, ZLogicalFormula actual) {
        throw new NotImplementedException();
    }
}
