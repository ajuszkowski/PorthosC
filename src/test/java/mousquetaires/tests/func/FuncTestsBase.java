package mousquetaires.tests.func;

import mousquetaires.app.modules.AppModule;
import mousquetaires.app.modules.AppModuleName;
import mousquetaires.app.modules.dartagnan.Dartagnan;
import mousquetaires.app.options.AppOptions;
import mousquetaires.models.MemoryModelName;
import mousquetaires.utils.logging.LogLevel;

import java.io.File;


public abstract class FuncTestsBase {

    protected final String targetsDirectory = "src/test/resources/targets";

    protected Dartagnan createDartagnanModule(String inputProgramFile, MemoryModelName sourceModel) {
        return (Dartagnan) createModule(AppModuleName.Dartagnan, inputProgramFile, sourceModel, null);
    }

    private AppModule createModule(AppModuleName moduleName,
                                   String inputProgramFile,
                                   MemoryModelName sourceModel,
                                   MemoryModelName targetModel) {
        AppOptions options = new AppOptions();
        options.inputProgramFile = new File(inputProgramFile);
        options.sourceModel = sourceModel;
        options.targetModel = targetModel;
        options.logLevel = LogLevel.Info;

        switch (moduleName) {
            case Porthos:
                throw new IllegalArgumentException(); // TODO
            case Dartagnan:
                return new Dartagnan(options);
            case Aramis:
                throw new IllegalArgumentException();  // TODO
            default:
                throw new IllegalArgumentException(moduleName.toString());
        }
    }
}
