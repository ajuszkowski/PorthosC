package mousquetaires.tests.unit.languages.converters.tozformula;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.InputExtensions;
import mousquetaires.languages.InputLanguage;
import mousquetaires.languages.converters.InputParserBase;
import mousquetaires.languages.converters.toxgraph.Ytree2XgraphConverter;
import mousquetaires.languages.converters.toytree.YtreeParser;
import mousquetaires.languages.converters.tozformula.XProgram2ZformulaEncoder;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.program.XCyclicProgram;
import mousquetaires.languages.syntax.xgraph.program.XProgram;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.zformula.ZFormulaBuilder;
import mousquetaires.languages.transformers.xgraph.XProgramTransformer;
import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.tests.TestFailedException;
import mousquetaires.tests.unit.Assertion;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class C2Zformula_UnitTestBase extends AbstractConverterUnitTest<BoolExpr> {

    protected abstract MemoryModel.Kind memoryModel();

    @Override
    protected Iterator<? extends BoolExpr> parseTestFile(String testFile) {
        try {
            DataModel dataModel = null; // TODO: consider data model also
            int unrollBound = 6; // TODO: PASS IT AS TEST PARAMETER (object testSettings)
            File file = new File(testFile);
            InputLanguage language = InputExtensions.parseProgramLanguage(file.getName());
            InputParserBase parser = new YtreeParser(file, language);
            YSyntaxTree internalRepr = parser.parseFile();
            Ytree2XgraphConverter converter = new Ytree2XgraphConverter(language, memoryModel(), dataModel);
            XCyclicProgram program = converter.convert(internalRepr);
            XProgram unrolledProgram = XProgramTransformer.unroll(program, unrollBound);
            Context ctx = new Context();
            XProgram2ZformulaEncoder encoder = new XProgram2ZformulaEncoder(ctx, unrolledProgram);
            ZFormulaBuilder formulaBuilder = new ZFormulaBuilder(ctx);
            encoder.encode(unrolledProgram, formulaBuilder);

            BoolExpr smtFormula = formulaBuilder.build();
            return CollectionUtils.createIteratorFrom(smtFormula);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }

    @Override
    protected Assertion getComparingAssertion(BoolExpr expected, BoolExpr actual) {
        throw new NotImplementedException();
    }
}
