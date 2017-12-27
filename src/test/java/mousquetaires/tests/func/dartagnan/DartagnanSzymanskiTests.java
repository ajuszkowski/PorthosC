package mousquetaires.tests.func.dartagnan;

import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.tests.func.FuncTestsBase;
import mousquetaires.models.MemoryModelName;
import org.junit.runner.RunWith;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

import static org.junit.Assert.assertEquals;


@RunWith(ZohhakRunner.class)
public class DartagnanSzymanskiTests extends FuncTestsBase {

    private final String szymanski_pts_rx = targetsDirectory + "/all_rx/szymanski.pts";
    private final String szymanski_pts_sc = targetsDirectory + "/all_sc/szymanski.pts";

    @TestWith({
            szymanski_pts_rx + ", " + "SC,    Reachable",
            szymanski_pts_rx + ", " + "TSO,   Reachable",
            szymanski_pts_rx + ", " + "PSO,   NonReachable",
            szymanski_pts_rx + ", " + "RMO,   NonReachable",
            szymanski_pts_rx + ", " + "Alpha, NonReachable",
            szymanski_pts_rx + ", " + "Power, NonReachable",
            szymanski_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_szymanski_pts_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }

    @TestWith({
            szymanski_pts_sc + ", " + "SC,    NonReachable",
            szymanski_pts_sc + ", " + "TSO,   NonReachable",
            szymanski_pts_sc + ", " + "PSO,   NonReachable",
            szymanski_pts_sc + ", " + "RMO,   NonReachable",
            szymanski_pts_sc + ", " + "Alpha, NonReachable",
            szymanski_pts_sc + ", " + "Power, NonReachable",
            szymanski_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_szymanski_pts_sc(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = createDartagnanModule(inputProgramFile, sourceModel).run();
        assertEquals(verdict.result, expected);
    }


}
