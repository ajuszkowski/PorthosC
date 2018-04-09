package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.memorymodels.wmm.MemoryModelName;
import mousquetaires.tests.unit.FuncTestPaths;
import org.junit.Ignore;

import static org.junit.Assert.assertEquals;


public class DartagnanLamportTest extends AbstractDartagnanFuncTest {

    private final String lamport_pts_rx    = FuncTestPaths.targetsDirectory + "all_rx/lamport.pts";
    private final String lamport_litmus_rx = FuncTestPaths.targetsDirectory + "all_rx/lamport.litmus";
    private final String lamport_pts_sc    = FuncTestPaths.targetsDirectory + "all_sc/lamport.pts";

    // == Relaxed operations: ==

    @TestWith({
            lamport_pts_rx + ", " + "SC,    Reachable",
            lamport_pts_rx + ", " + "TSO,   Reachable",
            lamport_pts_rx + ", " + "PSO,   NonReachable",
            lamport_pts_rx + ", " + "RMO,   NonReachable",
            lamport_pts_rx + ", " + "Alpha, NonReachable",
            lamport_pts_rx + ", " + "Power, NonReachable",
            lamport_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_lamport_pts_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    @Ignore("For now (as well as in original version) the NullPointerException is thrown at Program.initialize(Program.java:69)")
    @TestWith({
            lamport_litmus_rx + ", " + "SC,    Reachable",
            lamport_litmus_rx + ", " + "TSO,   Reachable",
            lamport_litmus_rx + ", " + "PSO,   NonReachable",
            lamport_litmus_rx + ", " + "RMO,   NonReachable",
            lamport_litmus_rx + ", " + "Alpha, NonReachable",
            lamport_litmus_rx + ", " + "Power, NonReachable",
            lamport_litmus_rx + ", " + "ARM,   NonReachable",
    })
    public void test_lamport_litmus_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    // == Sequentially consistent operations: ==

    @TestWith({
            lamport_pts_sc + ", " + "SC,    NonReachable",
            lamport_pts_sc + ", " + "TSO,   NonReachable",
            lamport_pts_sc + ", " + "PSO,   NonReachable",
            lamport_pts_sc + ", " + "RMO,   NonReachable",
            lamport_pts_sc + ", " + "Alpha, NonReachable",
            lamport_pts_sc + ", " + "Power, NonReachable",
            lamport_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_lamport_pts_sc(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
