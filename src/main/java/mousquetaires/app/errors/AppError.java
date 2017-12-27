package mousquetaires.app.errors;

public abstract class AppError {
    public enum  Severity {
        Critical,
        NonCritical,
    }

    public final Severity severity;
    public final String message;

    AppError(Severity severity, String message) {
        this.severity = severity;
        this.message = getClass().getName() + ": " + message;
    }
}
