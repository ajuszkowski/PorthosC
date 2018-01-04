package mousquetaires.app.errors;

import java.io.IOException;


public class IOError extends AppError {

    public IOError(IOException exception) {
        super(Severity.Critical, exception);
    }
}
