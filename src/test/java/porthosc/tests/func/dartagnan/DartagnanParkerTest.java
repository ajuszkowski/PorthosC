package porthosc.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import porthosc.app.modules.dartagnan.DartagnanVerdict;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.unit.FuncTestPaths;

import static org.junit.Assert.assertEquals;


public class DartagnanParkerTest extends AbstractDartagnanFuncTest {

    private final String parker_pts_rx    = FuncTestPaths.targetsDirectory + "all_rx/parker.pts";
    private final String parker_litmus_rx = FuncTestPaths.targetsDirectory + "all_rx/parker.litmus";
    private final String parker_pts_sc    = FuncTestPaths.targetsDirectory + "all_sc/parker.pts";

    // == Relaxed operations: ==

    @TestWith({
            parker_pts_rx + ", " + "SC,    Reachable",
            parker_pts_rx + ", " + "TSO,   Reachable",
            parker_pts_rx + ", " + "PSO,   Reachable",
            parker_pts_rx + ", " + "RMO,   Reachable",
            parker_pts_rx + ", " + "Alpha, Reachable",
            parker_pts_rx + ", " + "Power, Reachable",
            parker_pts_rx + ", " + "ARM,   Reachable",
    })
    public void test_parker_pts_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    @TestWith({
            parker_litmus_rx + ", " + "SC,    NonReachable",
            parker_litmus_rx + ", " + "TSO,   NonReachable",
            parker_litmus_rx + ", " + "PSO,   NonReachable",
            parker_litmus_rx + ", " + "RMO,   NonReachable",
            parker_litmus_rx + ", " + "Alpha, NonReachable",
            parker_litmus_rx + ", " + "Power, NonReachable",
            parker_litmus_rx + ", " + "ARM,   NonReachable",
    })
    public void test_parker_litmus_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    // == Sequentially consistent operations: ==

    @TestWith({
            parker_pts_sc + ", " + "SC,    NonReachable",
            parker_pts_sc + ", " + "TSO,   NonReachable",
            parker_pts_sc + ", " + "PSO,   NonReachable",
            parker_pts_sc + ", " + "RMO,   NonReachable",
            parker_pts_sc + ", " + "Alpha, NonReachable",
            parker_pts_sc + ", " + "Power, NonReachable",
            parker_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_parker_pts_sc(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
