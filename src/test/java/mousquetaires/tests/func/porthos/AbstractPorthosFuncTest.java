package mousquetaires.tests.func.porthos;

import mousquetaires.app.modules.porthos.PorthosMode;
import mousquetaires.app.modules.porthos.PorthosModule;
import mousquetaires.app.modules.porthos.PorthosOptions;
import mousquetaires.app.modules.porthos.PorthosVerdict;
import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.tests.func.AbstractFuncTest;

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
