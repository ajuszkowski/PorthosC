package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.memorymodels.wmm.MemoryModelKind;
import mousquetaires.tests.unit.FuncTestPaths;

import static org.junit.Assert.assertEquals;


public class DartagnanPetersonTest extends AbstractDartagnanFuncTest {

    private final String peterson_pts_rx    = FuncTestPaths.targetsDirectory + "all_rx/peterson.pts";
    private final String peterson_litmus_rx = FuncTestPaths.targetsDirectory + "all_rx/peterson.litmus";
    private final String peterson_pts_sc    = FuncTestPaths.targetsDirectory + "all_sc/peterson.pts";

    // == Relaxed operations: ==

    @TestWith({
            peterson_pts_rx + ", " + "SC,    Reachable",
            peterson_pts_rx + ", " + "TSO,   Reachable",
            peterson_pts_rx + ", " + "PSO,   NonReachable",
            peterson_pts_rx + ", " + "RMO,   NonReachable",
            peterson_pts_rx + ", " + "Alpha, NonReachable",
            peterson_pts_rx + ", " + "Power, NonReachable",
            peterson_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_peterson_pts_rx(String inputProgramFile, MemoryModelKind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    @TestWith({
            peterson_litmus_rx + ", " + "SC,    NonReachable",
            peterson_litmus_rx + ", " + "TSO,   NonReachable",
            peterson_litmus_rx + ", " + "PSO,   NonReachable",
            peterson_litmus_rx + ", " + "RMO,   NonReachable",
            peterson_litmus_rx + ", " + "Alpha, NonReachable",
            peterson_litmus_rx + ", " + "Power, NonReachable",
            peterson_litmus_rx + ", " + "ARM,   NonReachable",
    })
    public void test_peterson_litmus_rx(String inputProgramFile, MemoryModelKind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    // == Sequentially consistent operations: ==

    @TestWith({
            peterson_pts_sc + ", " + "SC,    NonReachable",
            peterson_pts_sc + ", " + "TSO,   NonReachable",
            peterson_pts_sc + ", " + "PSO,   NonReachable",
            peterson_pts_sc + ", " + "RMO,   NonReachable",
            peterson_pts_sc + ", " + "Alpha, NonReachable",
            peterson_pts_sc + ", " + "Power, NonReachable",
            peterson_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_peterson_pts_sc(String inputProgramFile, MemoryModelKind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
