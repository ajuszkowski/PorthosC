package mousquetaires.tests.func.dartagnan;

import mousquetaires.app.modules.dartagnan.DartagnanModule;
import mousquetaires.app.modules.dartagnan.DartagnanOptions;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.memorymodels.wmm.MemoryModelName;
import mousquetaires.tests.func.AbstractFuncTest;

import java.io.File;


class AbstractDartagnanFuncTest extends AbstractFuncTest {

    protected DartagnanVerdict runTest(String inputProgramFile, MemoryModelName sourceModel) {
        DartagnanOptions options = new DartagnanOptions();
        options.inputProgramFile = new File(inputProgramFile);
        options.sourceModel = sourceModel;

        DartagnanModule module = new DartagnanModule(options);
        return (DartagnanVerdict) runModule(module);
    }
}
