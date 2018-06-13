package porthosc.tests.func;

import com.googlecode.zohhak.api.runners.ZohhakRunner;
import porthosc.app.errors.AppError;
import porthosc.app.modules.AppVerdict;
import porthosc.app.modules.AppModule;
import porthosc.tests.AbstractTest;
import porthosc.tests.TestFailedException;
import org.junit.runner.RunWith;


@RunWith(ZohhakRunner.class)
public abstract class AbstractFuncTest extends AbstractTest {

    protected AppVerdict runModule(AppModule module) {
        AppVerdict verdict = module.run();
        if (verdict.hasErrors()) {
            for (AppError error : verdict.getErrors()) {
                logError(error);
            }
            throw new TestFailedException(verdict);
        }
        return verdict;
    }
}
