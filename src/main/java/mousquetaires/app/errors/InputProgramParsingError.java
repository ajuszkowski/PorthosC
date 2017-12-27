package mousquetaires.app.errors;

public class InputProgramParsingError extends AppError {
    InputProgramParsingError(Severity severity, String message) {
        super(severity, message);
    }
}
