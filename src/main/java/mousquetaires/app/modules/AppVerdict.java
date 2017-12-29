package mousquetaires.app.modules;

import mousquetaires.app.errors.AppError;

import java.util.ArrayList;
import java.util.List;


public abstract class AppVerdict {

    protected final List<AppError> errors = new ArrayList<>();
    protected double elapsedTimeSec;  // todo: in future, separate to time spent on encoding and on solving.
    private transient long startTime;

    public void onStartExecution() {
        startTime = System.nanoTime();
    }

    public void onFinishExecution() {
        elapsedTimeSec = (System.nanoTime() - startTime) / 10^9;
    }

    public void addError(AppError error) {
        errors.add(error);
    }

    // TODO: store original SMT formula here + num of iterations
}
