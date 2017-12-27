package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.tests.func.FuncTestsBase;
import mousquetaires.models.MemoryModelName;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(ZohhakRunner.class)
public class DartagnanDekkerTests extends FuncTestsBase {

    private final String dekker_pts_rx = targetsDirectory + "/all_rx/dekker.pts";
    private final String dekker_pts_sc = targetsDirectory + "/all_sc/dekker.pts";

    @TestWith({
            dekker_pts_rx + ", " + "SC,    Reachable",
            dekker_pts_rx + ", " + "TSO,   Reachable",
            dekker_pts_rx + ", " + "PSO,   NonReachable",
            dekker_pts_rx + ", " + "RMO,   NonReachable",
            dekker_pts_rx + ", " + "Alpha, NonReachable",
            dekker_pts_rx + ", " + "Power, NonReachable",
            dekker_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_dekker_pts_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }

    @TestWith({
            dekker_pts_sc + ", " + "SC,    NonReachable",
            dekker_pts_sc + ", " + "TSO,   NonReachable",
            dekker_pts_sc + ", " + "PSO,   NonReachable",
            dekker_pts_sc + ", " + "RMO,   NonReachable",
            dekker_pts_sc + ", " + "Alpha, NonReachable",
            dekker_pts_sc + ", " + "Power, NonReachable",
            dekker_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_dekker_pts_sc(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }


}
