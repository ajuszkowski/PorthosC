package mousquetaires.languages.internalrepr.variables;

import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.types.InternalType;


public abstract class InternalVariable implements InternalEntity {

    public enum Kind {
        Local,
        Global,
    }

    public final String name;
    public final InternalType type;

    public InternalVariable(String name, InternalType type) {
        this.name = name;
        this.type = type;
    }
}
