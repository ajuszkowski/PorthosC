package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.tests.func.FuncTestsBase;
import mousquetaires.models.MemoryModelName;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(ZohhakRunner.class)
public class DartagnanPetersonTests extends FuncTestsBase {

    private final String peterson_pts_rx = targetsDirectory + "/all_rx/peterson.pts";
    private final String peterson_pts_sc = targetsDirectory + "/all_sc/peterson.pts";

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
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }

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
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }


}
