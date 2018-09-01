package porthosc.app.errors;

public class UnexpectedError extends AppError {

    public UnexpectedError(Severity severity, Exception e) {
        super(severity, e);
    }

    @Override
    public String toString() {
        return "Unexpected error: " + super.toString();
    }
}
