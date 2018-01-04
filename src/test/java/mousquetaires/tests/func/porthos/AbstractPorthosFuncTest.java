package mousquetaires.tests.func.porthos;

import mousquetaires.app.modules.porthos.PorthosMode;
import mousquetaires.app.modules.porthos.PorthosModule;
import mousquetaires.app.modules.porthos.PorthosOptions;
import mousquetaires.app.modules.porthos.PorthosVerdict;
import mousquetaires.memorymodels.MemoryModelName;
import mousquetaires.tests.func.AbstractFuncTest;

import java.io.File;


class AbstractPorthosFuncTest extends AbstractFuncTest {

    protected PorthosVerdict runTest(String inputProgramFile,
                                     MemoryModelName sourceModel,
                                     MemoryModelName targetModel,
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
