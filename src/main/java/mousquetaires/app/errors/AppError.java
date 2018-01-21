package mousquetaires.app.errors;

import mousquetaires.utils.exceptions.ExceptionUtils;
import mousquetaires.utils.StringUtils;


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
        this.message = getClass().getName() + ": " + StringUtils.nonNull(message);
        this.additionalInfo = StringUtils.nonNull(additionalInfo);
    }

    AppError(Severity severity, Exception e) {
        this.severity = severity;
        this.message = StringUtils.nonNull(e.getMessage());
        this.additionalInfo = ExceptionUtils.getStackTrace(e);
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

    public String getAdditionalMessage() {
        return additionalInfo;
    }
}
