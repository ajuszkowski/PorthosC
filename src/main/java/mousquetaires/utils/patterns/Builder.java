package mousquetaires.utils.patterns;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.exceptions.BuilderException;


public abstract class Builder<T> {

    private boolean isBuilt = false;

    public abstract T build();

    protected void finishBuilding() {
        if (isBuilt) {
            throw new BuilderException(getAlreadyFinishedMessage());
        }
        isBuilt = true;
    }

    // TODO : check this method and re-implement other builders
    protected <S> void add(S element, ImmutableList.Builder<S> collection) {
        throwIfAlreadyBuilt();
        collection.add(element);
    }

    protected <S> void addAll(Iterable<S> from, ImmutableList.Builder<S> to) {
        throwIfAlreadyBuilt();
        to.addAll(from);
    }

    protected void throwIfAlreadyBuilt() {
        if (isBuilt) {
            throw new BuilderException(getAlreadyFinishedMessage());
        }
    }

    private final String getAlreadyFinishedMessage() {
        return getClass().getName() + " has already finished.";
    }

    private final String getNotYetFinishedMessage() {
        return getClass().getName() + " is not yet finished.";
    }
}
