package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.tests.func.FuncTestsBase;
import mousquetaires.models.MemoryModelName;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(ZohhakRunner.class)
public class DartagnanLamportTests extends FuncTestsBase {

    private final String lamport_pts_rx = targetsDirectory + "/all_rx/lamport.pts";
    private final String lamport_pts_sc = targetsDirectory + "/all_sc/lamport.pts";

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
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }

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
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }


}
