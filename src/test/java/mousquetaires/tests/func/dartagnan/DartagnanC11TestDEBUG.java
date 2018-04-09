package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.memorymodels.wmm.MemoryModelName;

import static org.junit.Assert.assertEquals;


public class DartagnanC11TestDEBUG extends AbstractDartagnanFuncTest {

    @TestWith({
            "src/test/resources/converters/c11/statements/branchingStatement.c, TSO, Reachable",
    })
    public void test_bakery_pts_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
