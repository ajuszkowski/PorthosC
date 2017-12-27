package mousquetaires.tests.func;

import mousquetaires.app.modules.AppModule;
import mousquetaires.app.modules.AppModuleName;
import mousquetaires.app.modules.AppVerdict;
import mousquetaires.app.modules.dartagnan.Dartagnan;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.app.options.AppOptions;
import mousquetaires.models.MemoryModelName;
import mousquetaires.utils.logging.LogLevel;

import java.io.File;
import java.io.IOException;


public abstract class FuncTestsBase {
    protected final String targetsDirectory = "src/test/resources/targets";


    protected DartagnanVerdict runDartagnan(String inputProgramFile, MemoryModelName sourceModel) {
        return (DartagnanVerdict) run(AppModuleName.Dartagnan, inputProgramFile, sourceModel, null);
    }

    protected AppVerdict run(AppModuleName moduleName,
                             String inputProgramFile,
                             MemoryModelName sourceModel,
                             MemoryModelName targetModel) {
        AppModule module = createModule(moduleName, inputProgramFile, sourceModel, targetModel);
        try {
            return module.run();
        } catch (IOException e) {
            // todo: log
            e.printStackTrace();
            return null;
        }
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
