package mousquetaires.app.errors;

public class UnrecognisedError extends AppError {

    public UnrecognisedError(Severity severity, Exception e) {
        super(severity, e);
    }
}
