package mousquetaires.app.modules;

public class Timer {

    private transient double startTime;
    private double elapsedTimeSec;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        elapsedTimeSec = (System.currentTimeMillis() - startTime) * 1.0 / 1000;
    }

    public double getElapsedTimeSec() {
        return elapsedTimeSec;
    }
}
