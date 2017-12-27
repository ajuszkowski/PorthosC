package mousquetaires.app.errors;

public abstract class AppError {
    public enum  Severity {
        Critical,
        NonCritical,
    }

    private final Severity severity;
    private final String message;
    private final String additionalInfo;

    AppError(Severity severity, String message, String additionalInfo) {
        this.severity = severity;
        this.message = getClass().getName() + ": " + message;
        this.additionalInfo = additionalInfo;
    }

    AppError(Severity severity, String message) {
        this(severity, message, "");
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
