package porthosc.tests.func.porthos;

import com.googlecode.zohhak.api.TestWith;
import porthosc.app.modules.porthos.PorthosMode;
import porthosc.app.modules.verdicts.PorthosVerdict;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.unit.FuncTestPaths;

import static org.junit.Assert.assertEquals;


public class PorthosBakeryTest extends AbstractPorthosFuncTest {

    private final String bakery_pts_rx    = FuncTestPaths.targetsDirectory + "all_rx/bakery.pts";
    //private final String bakery_litmus_rx = targetsDirectory + "all_rx/bakery.litmus";
    //private final String bakery_pts_sc    = targetsDirectory + "all_sc/bakery.pts";

    // == Relaxed operations: ==

    // TODO: check results with respect to the information at p. 53 Alglave's these
    // TODO: and fromValue new tests that illustrate non-portability for different architectures -- will help in diploma
    @TestWith({
            bakery_pts_rx + ", " + "SC,    TSO,   StateInclusion, NonStatePortable",  // ~5 sec
            //bakery_pts_rx + ", " + "SC,    PSO,   StateInclusion, NonStatePortable",  // ~4 sec
            //bakery_pts_rx + ", " + "SC,    RMO,   StateInclusion, NonStatePortable",  // ~11 sec
            //bakery_pts_rx + ", " + "SC,    Alpha, StateInclusion, NonStatePortable",  // ~6 sec
            //bakery_pts_rx + ", " + "SC,    Power, StateInclusion, NonStatePortable",  // ~50 sec
            //bakery_pts_rx + ", " + "SC,    ARM,   StateInclusion, NonStatePortable",  // ~1 m 33 sec
            //
            bakery_pts_rx + ", " + "TSO,   SC,    StateInclusion, StatePortable",  // ~2 sec
            //bakery_pts_rx + ", " + "TSO,   PSO,   StateInclusion, StatePortable",  // ~3 sec
            //bakery_pts_rx + ", " + "TSO,   RMO,   StateInclusion, StatePortable",  // ~11 sec
            //bakery_pts_rx + ", " + "TSO,   Alpha, StateInclusion, StatePortable",  // ~11 sec
            //bakery_pts_rx + ", " + "TSO,   Power, StateInclusion, StatePortable",  // ~1 min
            //bakery_pts_rx + ", " + "TSO,   ARM,   StateInclusion, StatePortable",  // ~1m 37s
            //
            bakery_pts_rx + ", " + "PSO,   SC,    StateInclusion, StatePortable",  // ~2 sec
            //bakery_pts_rx + ", " + "PSO,   TSO,   StateInclusion, StatePortable",  // ~2 sec
            //bakery_pts_rx + ", " + "PSO,   RMO,   StateInclusion, StatePortable",  // ~11 sec
            //bakery_pts_rx + ", " + "PSO,   Alpha, StateInclusion, StatePortable",  // ~11 sec
            //bakery_pts_rx + ", " + "PSO,   Power, StateInclusion, StatePortable",  // ~47 sec
            //bakery_pts_rx + ", " + "PSO,   ARM,   StateInclusion, StatePortable",  // ~1 min 32 sec
            //
            bakery_pts_rx + ", " + "RMO,   SC,    StateInclusion, StatePortable",  // ~5 sec
            //bakery_pts_rx + ", " + "RMO,   TSO,   StateInclusion, StatePortable",  // ~5 sec
            //bakery_pts_rx + ", " + "RMO,   PSO,   StateInclusion, StatePortable",  // ~5 sec
            //bakery_pts_rx + ", " + "RMO,   Alpha, StateInclusion, StatePortable",  // ~39 sec
            //bakery_pts_rx + ", " + "RMO,   Power, StateInclusion, StatePortable",  // ~17 sec
            //bakery_pts_rx + ", " + "RMO,   ARM,   StateInclusion, StatePortable",  // ~2m
            //
            bakery_pts_rx + ", " + "Alpha, SC,    StateInclusion, StatePortable",  // ~5s
            //bakery_pts_rx + ", " + "Alpha, TSO,   StateInclusion, StatePortable",  // ~5s
            //bakery_pts_rx + ", " + "Alpha, PSO,   StateInclusion, StatePortable",  // ~5s
            //bakery_pts_rx + ", " + "Alpha, RMO,   StateInclusion, StatePortable",  // ~30s
            //bakery_pts_rx + ", " + "Alpha, Power, StateInclusion, StatePortable",  // ~15s
            //bakery_pts_rx + ", " + "Alpha, ARM,   StateInclusion, StatePortable",  // ~20s
            //
            bakery_pts_rx + ", " + "Power, SC,    StateInclusion, StatePortable",  // ~10s
            //bakery_pts_rx + ", " + "Power, TSO,   StateInclusion, StatePortable",  // ~20s
            //bakery_pts_rx + ", " + "Power, PSO,   StateInclusion, StatePortable",  // ~10s
            //bakery_pts_rx + ", " + "Power, RMO,   StateInclusion, StatePortable",  // ~1.3m
            //bakery_pts_rx + ", " + "Power, Alpha, StateInclusion, StatePortable",  // ~1.5m
            //bakery_pts_rx + ", " + "Power, ARM,   StateInclusion, StatePortable",  // ~10s
            //
            //bakery_pts_rx + ", " + "ARM,   SC,    StateInclusion, StatePortable",  // ~35s
            bakery_pts_rx + ", " + "ARM,   TSO,   StateInclusion, StatePortable",  // ~5m
            //bakery_pts_rx + ", " + "ARM,   PSO,   StateInclusion, StatePortable",  // ~35s
            //bakery_pts_rx + ", " + "ARM,   RMO,   StateInclusion, StatePortable",  // ~2m 45s
            //bakery_pts_rx + ", " + "ARM,   Alpha, StateInclusion, StatePortable",  // ~30s
            //bakery_pts_rx + ", " + "ARM,   Power, StateInclusion, StatePortable",  // ~40s
    })
    public void test_bakery_pts_rx(String inputProgramFile,
                                   MemoryModel.Kind sourceModel,
                                   MemoryModel.Kind targetModel,
                                   PorthosMode mode,
                                   PorthosVerdict.Status expected) {
        PorthosVerdict verdict = runTest(inputProgramFile, sourceModel, targetModel, mode);
        assertEquals(expected, verdict.result);
    }

    //@TestWith({
    //        bakery_litmus_rx + ", " + "SC,    NonStatePortable",
    //        bakery_litmus_rx + ", " + "TSO,   NonStatePortable",
    //        bakery_litmus_rx + ", " + "PSO,   NonStatePortable",
    //        bakery_litmus_rx + ", " + "RMO,   NonStatePortable",
    //        bakery_litmus_rx + ", " + "Alpha, NonStatePortable",
    //        bakery_litmus_rx + ", " + "Power, NonStatePortable",
    //        bakery_litmus_rx + ", " + "ARM,   NonStatePortable",
    //})
    //public void test_bakery_litmus_rx(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
    //    DartagnanVerdict verdicts = runTest(inputProgramFile, sourceModel);
    //    assertEquals(expected, verdicts.result);
    //}
    //
    //// == Sequentially consistent operations: ==
    //
    //@TestWith({
    //        bakery_pts_sc + ", " + "SC,    NonStatePortable",
    //        bakery_pts_sc + ", " + "TSO,   NonStatePortable",
    //        bakery_pts_sc + ", " + "PSO,   NonStatePortable",
    //        bakery_pts_sc + ", " + "RMO,   NonStatePortable",
    //        bakery_pts_sc + ", " + "Alpha, NonStatePortable",
    //        bakery_pts_sc + ", " + "Power, NonStatePortable",
    //        bakery_pts_sc + ", " + "ARM,   NonStatePortable",
    //})
    //public void test_bakery_pts_sc(String inputProgramFile, MemoryModelName sourceModel, DartagnanVerdict.Status expected) {
    //    DartagnanVerdict verdicts = runTest(inputProgramFile, sourceModel);
    //    assertEquals(expected, verdicts.result);
    //}

}

// Code to generate tests:
// models = ['SC', 'TSO', 'PSO', 'RMO', 'Alpha', 'Power', 'ARM',]
//for m1 in models:
//    for m2 in models:
//        if m1 == m2:
//            continue
//        maxlen =  5# max(len(m1), len(m2))
//        print ('bakery_pts_rx + ", " + "{}, {}, StatePortable",'.format( m1+' '*(maxlen-len(m1)), m2+' '*(maxlen-len(m2)  ) ))