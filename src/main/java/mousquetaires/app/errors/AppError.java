package mousquetaires.app.errors;

import mousquetaires.utils.ExceptionUtils;
import mousquetaires.utils.StringUtils;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;


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
        this.message = getClass().getName() + ": " + StringUtils.notNull(message);
        this.additionalInfo = StringUtils.notNull(additionalInfo);
    }

    AppError(Severity severity, Exception e) {
        this.severity = severity;
        this.message = StringUtils.notNull(e.getMessage());
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
