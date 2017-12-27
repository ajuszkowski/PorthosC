package mousquetaires.tests.func.dartagnan;

import mousquetaires.app.modules.dartagnan.DartagnanModule;
import mousquetaires.app.modules.dartagnan.DartagnanOptions;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.models.MemoryModelName;
import mousquetaires.tests.func.BaseFuncTest;

import java.io.File;


class BaseDartagnanFuncTest extends BaseFuncTest {

    protected DartagnanVerdict runTest(String inputProgramFile, MemoryModelName sourceModel) {
        DartagnanOptions options = new DartagnanOptions();
        options.inputProgramFile = new File(inputProgramFile);
        options.sourceModel = sourceModel;

        DartagnanModule module = new DartagnanModule(options);
        return (DartagnanVerdict) run(module);
    }
}
