package mousquetaires.languages.xrepr.memory;


import mousquetaires.languages.common.types.YXType;
import mousquetaires.languages.xrepr.XEntity;

import java.util.Objects;


public abstract class XLocation implements XEntity {
    public enum Kind {
        Local,
        Shared,
    }

    public final String name;
    public final YXType type;
    public final Kind kind;

    public XLocation(String name, YXType type, Kind kind) {
        this.name = name;
        this.type = type;
        this.kind = kind;
    }

    @Override
    public String toString() {
        String prefix = kind == Kind.Local ? "%" : "@";
        return prefix + name + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XLocation)) return false;
        XLocation location = (XLocation) o;
        return Objects.equals(name, location.name) &&
                Objects.equals(type, location.type) &&
                kind == location.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, kind);
    }
}
