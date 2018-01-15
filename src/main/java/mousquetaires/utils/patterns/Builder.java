package mousquetaires.utils.patterns;

import com.google.common.collect.ImmutableSet;


public abstract class Builder<T> {

    private boolean isBuilt = false;

    public abstract T build();

    protected void finish() {
        isBuilt = true;
    }

    protected boolean isBuilt() {
        return isBuilt;
    }

    // TODO : check this method and re-implement other builders
    protected <S> void add(S element, ImmutableSet.Builder<S> set) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        set.add(element);
    }

    protected final String getAlreadyFinishedMessage() {
        return getClass().getName() + " has already finished.";
    }

    protected final String getNotYetFinishedMessage() {
        return getClass().getName() + " is not yet finished.";
    }
}
