package mousquetaires.tests;

import mousquetaires.app.errors.AppError;

import java.io.File;


public abstract class AbstractTest {

    protected void logError(AppError e) {
        // todo: log
        System.out.println(e.getMessage());
        System.out.println(e.getAdditionalMessage());
    }

    protected String getTestsRoot() {
        File path = new File(System.getProperty("user.dir") + "/build/test/");
        if (!path.isDirectory()) {
            throw new IllegalStateException("Cannot find test-output directory: " + path.getAbsolutePath());
        }
        return path.getAbsolutePath();
    }
}
