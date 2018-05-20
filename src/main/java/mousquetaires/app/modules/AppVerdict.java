package mousquetaires.app.modules;

import mousquetaires.app.errors.AppError;

import java.util.ArrayList;
import java.util.List;


public abstract class AppVerdict {

    private transient long startTime;
    private double elapsedTimeSec;  // todo: in future, separate to time spent on encoding and on solving.

    private final List<AppError> errors = new ArrayList<>();

    public void onStartExecution() {
        startTime = System.currentTimeMillis();
    }

    public void onFinishExecution() {
        elapsedTimeSec = (System.currentTimeMillis() - startTime) * 1.0 / 1000;
    }

    public void addError(AppError error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public double getElapsedTimeSec() {
        return elapsedTimeSec;
    }

    public List<AppError> getErrors() {
        return errors;
    }

    // TODO: store original SMT formula here + num of iterations
}
