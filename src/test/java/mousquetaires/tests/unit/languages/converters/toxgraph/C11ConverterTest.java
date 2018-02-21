package mousquetaires.tests.unit.languages.converters.toxgraph;

import com.microsoft.z3.BoolExpr;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.XProgramToZ3Encoder;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;
import mousquetaires.utils.exceptions.NotImplementedException;


public abstract class C11ConverterTest extends AbstractConverterUnitTest {

    protected final String rootDirectory = convertersDirectory + "c11/";
    protected final String statementsDirectory = rootDirectory + "statements/";

    protected void runParserTest(String file, XProgram expected) {
        XProgram actual = runTest(file);
        XProgramToZ3Encoder encoder = new XProgramToZ3Encoder();

        BoolExpr result = encoder.encode(actual);

        //assertEquals("preludes:", expected.getPrelude(), actual.getPrelude());

        throw new NotImplementedException();
        //ImmutableList<XProcess> expectedProcesses = expected.getProcesses();
        //assertEquals("processes number does mismatch:", expectedProcesses.size(), actualProcesses.size());
        //for (int i = 0; i < expectedProcesses.size(); ++i) {
        //    assertEquals("mismatch in " + i + "th statement:", expectedProcesses.get(i), actualProcesses.get(i));
        //}
    }

}
