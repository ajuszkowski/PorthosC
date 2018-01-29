package mousquetaires.utils.patterns;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.exceptions.BuilderException;


public abstract class Builder<T> {

    private boolean isBuilt = false;

    public abstract T build();

    protected void finish() {
        if (isBuilt()) {
            throw new BuilderException(getAlreadyFinishedMessage());
        }
        isBuilt = true;
    }

    protected boolean isBuilt() {
        return isBuilt;
    }

    // TODO : check this method and re-implement other builders
    protected <S> void add(S element, ImmutableList.Builder<S> set) {
        throwIfAlreadyBuilt();
        set.add(element);
    }

    protected void throwIfAlreadyBuilt() {
        if (isBuilt()) {
            throw new BuilderException(getAlreadyFinishedMessage());
        }
    }

    protected final String getAlreadyFinishedMessage() {
        return getClass().getName() + " has already finished.";
    }

    protected final String getNotYetFinishedMessage() {
        return getClass().getName() + " is not yet finished.";
    }

}
