package mousquetaires.app.modules;

import mousquetaires.app.errors.AppError;

import java.util.ArrayList;
import java.util.List;


public abstract class AppVerdict {
    public final List<AppError> errors = new ArrayList<>();
}
