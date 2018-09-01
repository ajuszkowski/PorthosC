package porthosc.tests;

import porthosc.app.errors.AppError;
import porthosc.app.errors.UnexpectedError;
import porthosc.app.modules.verdicts.AppVerdict;

import java.util.ArrayList;
import java.util.List;


public class TestFailedException extends RuntimeException {
    public final List<AppError> errors;

    public TestFailedException(AppVerdict verdict) {
        this.errors = verdict.getErrors();
    }

    public TestFailedException(Exception e) {
        errors = new ArrayList<>(1);
        errors.add(new UnexpectedError(AppError.Severity.Critical, e));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Test errors:\n");
        int i = 1;
        for (AppError error : errors) {
            sb.append(i++).append(") ").append(error).append("--\n");
        }
        return sb.toString();
    }
}
