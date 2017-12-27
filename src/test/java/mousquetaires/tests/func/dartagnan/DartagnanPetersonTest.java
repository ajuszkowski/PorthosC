package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.models.MemoryModelName;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(ZohhakRunner.class)
public class DartagnanPetersonTest extends BaseDartagnanFuncTest {

    private final String peterson_pts_rx    = targetsDirectory + "/all_rx/peterson.pts";
    private final String peterson_litmus_rx = targetsDirectory + "/all_rx/peterson.litmus";
    private final String peterson_pts_sc    = targetsDirectory + "/all_sc/peterson.pts";

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
    public void test_peterson_pts_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
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
    public void test_peterson_litmus_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
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
    public void test_peterson_pts_sc(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }


}
