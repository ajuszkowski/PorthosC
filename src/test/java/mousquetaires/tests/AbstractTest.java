package mousquetaires.tests;

import mousquetaires.app.errors.AppError;


public abstract class AbstractTest {

    protected void logError(AppError e) {
        // todo: log
        System.out.println(e.getMessage());
        System.out.println(e.getAdditionalMessage());
    }
}
