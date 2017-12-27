package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.tests.func.FuncTestsBase;
import mousquetaires.models.MemoryModelName;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(ZohhakRunner.class)
public class DartagnanBurnsTests extends FuncTestsBase {

    private final String burns_pts_rx = targetsDirectory + "/all_rx/burns.pts";
    private final String burns_pts_sc = targetsDirectory + "/all_sc/burns.pts";

    @TestWith({
            burns_pts_rx + ", " + "SC,    Reachable",
            burns_pts_rx + ", " + "TSO,   Reachable",
            burns_pts_rx + ", " + "PSO,   NonReachable",
            burns_pts_rx + ", " + "RMO,   NonReachable",
            burns_pts_rx + ", " + "Alpha, NonReachable",
            burns_pts_rx + ", " + "Power, NonReachable",
            burns_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_burns_pts_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }

    @TestWith({
            burns_pts_sc + ", " + "SC,    NonReachable",
            burns_pts_sc + ", " + "TSO,   NonReachable",
            burns_pts_sc + ", " + "PSO,   NonReachable",
            burns_pts_sc + ", " + "RMO,   NonReachable",
            burns_pts_sc + ", " + "Alpha, NonReachable",
            burns_pts_sc + ", " + "Power, NonReachable",
            burns_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_burns_pts_sc(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }


}
