package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.memorymodels.MemoryModelName;
import mousquetaires.tests.unit.FuncTestPaths;

import static org.junit.Assert.assertEquals;


public class DartagnanBakeryTest extends AbstractDartagnanFuncTest {

    private final String bakery_pts_rx    = FuncTestPaths.targetsDirectory + "all_rx/bakery.pts";
    private final String bakery_litmus_rx = FuncTestPaths.targetsDirectory + "all_rx/bakery.litmus";
    private final String bakery_pts_sc    = FuncTestPaths.targetsDirectory + "all_sc/bakery.pts";

    // == Relaxed operations: ==

    @TestWith({
            bakery_pts_rx + ", " + "SC,    Reachable",
            bakery_pts_rx + ", " + "TSO,   Reachable",
            bakery_pts_rx + ", " + "PSO,   NonReachable",
            bakery_pts_rx + ", " + "RMO,   NonReachable",
            bakery_pts_rx + ", " + "Alpha, NonReachable",
            bakery_pts_rx + ", " + "Power, NonReachable",
            bakery_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_bakery_pts_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    @TestWith({
            bakery_litmus_rx + ", " + "SC,    NonReachable",
            bakery_litmus_rx + ", " + "TSO,   NonReachable",
            bakery_litmus_rx + ", " + "PSO,   NonReachable",
            bakery_litmus_rx + ", " + "RMO,   NonReachable",
            bakery_litmus_rx + ", " + "Alpha, NonReachable",
            bakery_litmus_rx + ", " + "Power, NonReachable",
            bakery_litmus_rx + ", " + "ARM,   NonReachable",
    })
    public void test_bakery_litmus_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    // == Sequentially consistent operations: ==

    @TestWith({
            bakery_pts_sc + ", " + "SC,    NonReachable",
            bakery_pts_sc + ", " + "TSO,   NonReachable",
            bakery_pts_sc + ", " + "PSO,   NonReachable",
            bakery_pts_sc + ", " + "RMO,   NonReachable",
            bakery_pts_sc + ", " + "Alpha, NonReachable",
            bakery_pts_sc + ", " + "Power, NonReachable",
            bakery_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_bakery_pts_sc(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
