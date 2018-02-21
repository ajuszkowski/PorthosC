package mousquetaires.tests.func;

import com.googlecode.zohhak.api.runners.ZohhakRunner;
import mousquetaires.app.errors.AppError;
import mousquetaires.app.modules.AppVerdict;
import mousquetaires.app.modules.AppModule;
import mousquetaires.tests.AbstractTest;
import mousquetaires.tests.TestFailedException;
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
