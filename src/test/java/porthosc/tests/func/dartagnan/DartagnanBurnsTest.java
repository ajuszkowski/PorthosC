package porthosc.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import porthosc.app.modules.verdicts.DartagnanVerdict;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.unit.FuncTestPaths;
import org.junit.Ignore;

import static org.junit.Assert.assertEquals;


public class DartagnanBurnsTest extends AbstractDartagnanFuncTest {

    private final String burns_pts_rx    = FuncTestPaths.targetsDirectory + "all_rx/burns.pts";
    private final String burns_litmus_rx = FuncTestPaths.targetsDirectory + "all_rx/burns.litmus";
    private final String burns_pts_sc    = FuncTestPaths.targetsDirectory + "all_sc/burns.pts";

    // == Relaxed operations: ==

    @TestWith({
            burns_pts_rx + ", " + "SC,    Reachable",
            burns_pts_rx + ", " + "TSO,   Reachable",
            burns_pts_rx + ", " + "PSO,   NonReachable",
            burns_pts_rx + ", " + "RMO,   NonReachable",
            burns_pts_rx + ", " + "Alpha, NonReachable",
            burns_pts_rx + ", " + "Power, NonReachable",
            burns_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_burns_pts_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    @Ignore("For now (as well as in original version) the NullPointerException is thrown at Atom.getRegs(Atom.java:51)")
    @TestWith({
            burns_litmus_rx + ", " + "SC,    Reachable",
            burns_litmus_rx + ", " + "TSO,   Reachable",
            burns_litmus_rx + ", " + "PSO,   NonReachable",
            burns_litmus_rx + ", " + "RMO,   NonReachable",
            burns_litmus_rx + ", " + "Alpha, NonReachable",
            burns_litmus_rx + ", " + "Power, NonReachable",
            burns_litmus_rx + ", " + "ARM,   NonReachable",
    })
    public void test_burns_litmus_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    // == Sequentially consistent operations: ==
    
    @TestWith({
            burns_pts_sc + ", " + "SC,    NonReachable",
            burns_pts_sc + ", " + "TSO,   NonReachable",
            burns_pts_sc + ", " + "PSO,   NonReachable",
            burns_pts_sc + ", " + "RMO,   NonReachable",
            burns_pts_sc + ", " + "Alpha, NonReachable",
            burns_pts_sc + ", " + "Power, NonReachable",
            burns_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_burns_pts_sc(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
