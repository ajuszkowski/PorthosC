package mousquetaires.languages.eventrepr.memory;

import mousquetaires.languages.internalrepr.InternalEntity;


public abstract class MemoryLocation implements InternalEntity, Cloneable {

    public enum Kind {
        Registry,
        SharedMemory,
    }

    public final String name;

    public final Kind kind;

    public MemoryLocation(String name, Kind kind) {
        this.name = name;
        this.kind = kind;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public MemoryLocation clone() {
        return this;
    }
}
