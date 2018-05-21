package mousquetaires.app.modules;

import mousquetaires.app.errors.AppError;

import java.util.ArrayList;
import java.util.List;


public abstract class AppVerdict {

    private final Timer interpretationTimer;
    private final Timer unrollingTimer;
    private final Timer programEncodingTimer;
    private final Timer memoryModelEncodingTimer;
    private final Timer solvingTimer;

    private final List<AppError> errors;

    public AppVerdict() {
        this.interpretationTimer = new Timer();
        this.unrollingTimer = new Timer();
        this.programEncodingTimer = new Timer();
        this.memoryModelEncodingTimer = new Timer();
        this.solvingTimer = new Timer();
        this.errors = new ArrayList<>();
    }

    public void onStartInterpretation() {
        System.out.println("Interpreting...");
        interpretationTimer.start();
    }

    public void onFinishInterpretation() {
        interpretationTimer.stop();
    }


    public void onStartUnrolling() {
        System.out.println("Unrolling...");
        unrollingTimer.start();
    }

    public void onFinishUnrolling() {
        unrollingTimer.stop();
    }


    public void onStartProgramEncoding() {
        System.out.println("Program encoding...");
        programEncodingTimer.start();
    }

    public void onFinishProgramEncoding() {
        programEncodingTimer.stop();
    }



    public void onStartModelEncoding() {
        System.out.println("Memory model encoding...");
        memoryModelEncodingTimer.start();
    }

    public void onFinishModelEncoding() {
        memoryModelEncodingTimer.stop();
    }


    public void onStartSolving() {
        System.out.println("Solving...");
        solvingTimer.start();
    }

    public void onFinishSolving() {
        solvingTimer.stop();
    }


    public void addError(AppError error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public List<AppError> getErrors() {
        return errors;
    }

    // TODO: store original SMT formula here + num of iterations
}
