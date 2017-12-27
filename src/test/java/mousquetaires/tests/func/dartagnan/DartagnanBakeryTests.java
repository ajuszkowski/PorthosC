package mousquetaires.tests.func.dartagnan;

import mousquetaires.tests.func.FuncTestsBase;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.models.MemoryModelName;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.googlecode.zohhak.api.TestWith;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(ZohhakRunner.class)
public class DartagnanBakeryTests extends FuncTestsBase {

    private final String bakery_pts_rx = targetsDirectory + "/all_rx/bakery.pts";
    private final String bakery_pts_sc = targetsDirectory + "/all_sc/bakery.pts";

    @TestWith({
            bakery_pts_rx + ", " + "SC,    Reachable",
            bakery_pts_rx + ", " + "TSO,   Reachable",
            bakery_pts_rx + ", " + "PSO,   NonReachable",
            bakery_pts_rx + ", " + "RMO,   NonReachable",
            bakery_pts_rx + ", " + "Alpha, NonReachable",
            bakery_pts_rx + ", " + "Power, NonReachable",
            bakery_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_bakery_pts_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }

    @TestWith({
            bakery_pts_sc + ", " + "SC,    NonReachable",
            bakery_pts_sc + ", " + "TSO,   NonReachable",
            bakery_pts_sc + ", " + "PSO,   NonReachable",
            bakery_pts_sc + ", " + "RMO,   NonReachable",
            bakery_pts_sc + ", " + "Alpha, NonReachable",
            bakery_pts_sc + ", " + "Power, NonReachable",
            bakery_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_bakery_pts_sc(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }


}
