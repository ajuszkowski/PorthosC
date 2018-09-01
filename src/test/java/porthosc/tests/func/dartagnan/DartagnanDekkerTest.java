package porthosc.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import porthosc.app.modules.verdicts.DartagnanVerdict;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.unit.FuncTestPaths;

import static org.junit.Assert.assertEquals;


public class DartagnanDekkerTest extends AbstractDartagnanFuncTest {

    private final String dekker_pts_rx    = FuncTestPaths.targetsDirectory + "all_rx/dekker.pts";
    private final String dekker_litmus_rx = FuncTestPaths.targetsDirectory + "all_rx/dekker.litmus";
    private final String dekker_pts_sc    = FuncTestPaths.targetsDirectory + "all_sc/dekker.pts";

    // == Relaxed operations: ==

    @TestWith({
            dekker_pts_rx + ", " + "SC,    Reachable",
            dekker_pts_rx + ", " + "TSO,   Reachable",
            dekker_pts_rx + ", " + "PSO,   NonReachable",
            dekker_pts_rx + ", " + "RMO,   NonReachable",
            dekker_pts_rx + ", " + "Alpha, NonReachable",
            dekker_pts_rx + ", " + "Power, NonReachable",
            dekker_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_dekker_pts_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    @TestWith({
            dekker_litmus_rx + ", " + "SC,    NonReachable",
            dekker_litmus_rx + ", " + "TSO,   NonReachable",
            dekker_litmus_rx + ", " + "PSO,   NonReachable",
            dekker_litmus_rx + ", " + "RMO,   NonReachable",
            dekker_litmus_rx + ", " + "Alpha, NonReachable",
            dekker_litmus_rx + ", " + "Power, NonReachable",
            dekker_litmus_rx + ", " + "ARM,   NonReachable",
    })
    public void test_dekker_litmus_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    // == Sequentially consistent operations: ==

    @TestWith({
            dekker_pts_sc + ", " + "SC,    NonReachable",
            dekker_pts_sc + ", " + "TSO,   NonReachable",
            dekker_pts_sc + ", " + "PSO,   NonReachable",
            dekker_pts_sc + ", " + "RMO,   NonReachable",
            dekker_pts_sc + ", " + "Alpha, NonReachable",
            dekker_pts_sc + ", " + "Power, NonReachable",
            dekker_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_dekker_pts_sc(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }


}
