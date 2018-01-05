package mousquetaires.patterns;

public abstract class Builder<T> {

    private boolean isBuilt = false;

    public abstract T build();

    protected void setBuilt() {
        isBuilt = true;
    }

    protected boolean isBuilt() {
        return isBuilt;
    }

    protected final String getAlreadyFinishedMessage() {
        return getClass().getName() + " has already finished.";
    }

    protected final String getNotYetFinishedMessage() {
        return getClass().getName() + " is not yet finished.";
    }
}
