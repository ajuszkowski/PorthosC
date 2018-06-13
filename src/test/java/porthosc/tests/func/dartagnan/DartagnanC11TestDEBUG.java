package porthosc.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import porthosc.app.modules.dartagnan.DartagnanVerdict;
import porthosc.memorymodels.wmm.MemoryModel;

import static org.junit.Assert.assertEquals;


public class DartagnanC11TestDEBUG extends AbstractDartagnanFuncTest {

    @TestWith({
            "src/test/resources/conversion/c11/statements/branchingStatement.c, TSO, Reachable",
    })
    public void test_bakery_pts_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
