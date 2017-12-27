package mousquetaires.tests.func;

import mousquetaires.app.modules.dartagnan.DartagnanModule;
import mousquetaires.app.modules.dartagnan.DartagnanOptions;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.models.MemoryModelName;

import java.io.File;
import java.io.IOException;


public abstract class FuncTestsBase {

    protected final String targetsDirectory = "src/test/resources/targets";


    protected DartagnanVerdict runDartagnan(String inputProgramFile, MemoryModelName sourceModel) {
        DartagnanOptions options = new DartagnanOptions();
        options.inputProgramFile = new File(inputProgramFile);
        options.sourceModel = sourceModel;

        DartagnanModule module = new DartagnanModule(options);
        try {
            return module.run();
        } catch (IOException e) {
            logError(e);
            return null;
        }
    }

    private void logError(Exception e) {
        // todo: log
        e.printStackTrace();
    }
}
