package porthosc.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import porthosc.app.modules.verdicts.DartagnanVerdict;
import porthosc.memorymodels.wmm.MemoryModel;

import static org.junit.Assert.assertEquals;


public class DartagnanC11TestDEBUG extends AbstractDartagnanFuncTest {

    @TestWith({
            "src/test/resources/converters/c11/statements/type_declaration/branchingStatement.c, TSO, Reachable",
    })
    public void test_bakery_pts_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
