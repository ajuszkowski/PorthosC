package mousquetaires.tests.func;

import mousquetaires.app.modules.AppVerdict;
import mousquetaires.app.modules.IAppModule;

import java.io.IOException;


public abstract class AbstractFuncTest {

    protected final String targetsDirectory = "src/test/resources/targets";

    protected AppVerdict run(IAppModule module) {
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
