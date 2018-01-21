package mousquetaires.tests.unit.languages.converters.cmin2xrepr;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xrepr.XProgram;
import mousquetaires.languages.syntax.xrepr.processes.XParallelProcess;
import mousquetaires.tests.unit.languages.converters.AbstractConverterUnitTest;

import static org.junit.Assert.assertEquals;


public abstract class CminConverterTest extends AbstractConverterUnitTest {

    protected final String rootDirectory = convertersDirectory + "cmin/";

    protected void runParserTest(String file, XProgram expected) {
        XProgram actual = runTest(file);

        assertEquals("preludes:", expected.getPrelude(), actual.getPrelude());

        ImmutableList<XParallelProcess> actualProcesses = actual.getProcesses();
        ImmutableList<XParallelProcess> expectedProcesses = expected.getProcesses();
        assertEquals("processes number does mismatch:", expectedProcesses.size(), actualProcesses.size());
        for (int i = 0; i < expectedProcesses.size(); ++i) {
            assertEquals("mismatch in " + i + "th statement:", expectedProcesses.get(i), actualProcesses.get(i));
        }
    }

}
