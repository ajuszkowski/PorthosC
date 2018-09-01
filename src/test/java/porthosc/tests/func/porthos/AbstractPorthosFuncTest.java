package porthosc.tests.func.porthos;

import porthosc.app.modules.porthos.PorthosMode;
import porthosc.app.modules.porthos.PorthosModule;
import porthosc.app.modules.porthos.PorthosOptions;
import porthosc.app.modules.verdicts.PorthosVerdict;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.func.AbstractFuncTest;

import java.io.File;


class AbstractPorthosFuncTest extends AbstractFuncTest {

    protected PorthosVerdict runTest(String inputProgramFile,
                                     MemoryModel.Kind sourceModel,
                                     MemoryModel.Kind targetModel,
                                     PorthosMode mode) {
        PorthosOptions options = new PorthosOptions();
        options.inputProgramFile = new File(inputProgramFile);
        options.sourceModel = sourceModel;
        options.targetModel = targetModel;
        options.mode = mode;

        PorthosModule module = new PorthosModule(options);
        return (PorthosVerdict) runModule(module);
    }
}
