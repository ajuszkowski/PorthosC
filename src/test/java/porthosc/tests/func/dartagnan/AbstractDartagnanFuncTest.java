package porthosc.tests.func.dartagnan;

import porthosc.app.modules.dartagnan.DartagnanModule;
import porthosc.app.modules.dartagnan.DartagnanOptions;
import porthosc.app.modules.dartagnan.DartagnanVerdict;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.func.AbstractFuncTest;

import java.io.File;


class AbstractDartagnanFuncTest extends AbstractFuncTest {

    protected DartagnanVerdict runTest(String inputProgramFile, MemoryModel.Kind sourceModel) {
        DartagnanOptions options = new DartagnanOptions();
        options.inputProgramFile = new File(inputProgramFile);
        options.sourceModel = sourceModel;

        DartagnanModule module = new DartagnanModule(options);
        return (DartagnanVerdict) runModule(module);
    }
}
