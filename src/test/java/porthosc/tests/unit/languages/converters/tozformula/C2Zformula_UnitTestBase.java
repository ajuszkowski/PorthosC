package porthosc.tests.unit.languages.converters.tozformula;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import porthosc.languages.InputExtensions;
import porthosc.languages.InputLanguage;
import porthosc.languages.converters.InputParserBase;
import porthosc.languages.converters.toxgraph.Y2XConverter;
import porthosc.languages.converters.toytree.YtreeParser;
import porthosc.languages.converters.tozformula.XProgram2ZformulaEncoder;
import porthosc.languages.syntax.xgraph.program.XCyclicProgram;
import porthosc.languages.syntax.xgraph.program.XProgram;
import porthosc.languages.syntax.ytree.YSyntaxTree;
import porthosc.languages.transformers.xgraph.XProgramTransformer;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.TestFailedException;
import porthosc.tests.unit.Assertion;
import porthosc.tests.unit.languages.converters.AbstractConverterUnitTest;
import porthosc.utils.CollectionUtils;
import porthosc.utils.exceptions.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class C2Zformula_UnitTestBase extends AbstractConverterUnitTest<BoolExpr> {

    protected abstract MemoryModel.Kind memoryModel();

    @Override
    protected Iterator<? extends BoolExpr> parseTestFile(String testFile) {
        try {
            int unrollBound = 6; // TODO: PASS IT AS TEST PARAMETER (object testSettings)
            File file = new File(testFile);
            InputLanguage language = InputExtensions.parseProgramLanguage(file.getName());
            InputParserBase parser = new YtreeParser(file, language);
            YSyntaxTree internalRepr = parser.parseFile();
            Y2XConverter converter = new Y2XConverter(memoryModel());
            XCyclicProgram program = converter.convert(internalRepr);
            XProgram unrolledProgram = XProgramTransformer.unroll(program, unrollBound);
            Context ctx = new Context();
            XProgram2ZformulaEncoder encoder = new XProgram2ZformulaEncoder(ctx, unrolledProgram);
            return CollectionUtils.createIteratorFrom(encoder.encodeProgram(unrolledProgram));
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
