package mousquetaires.languages.internal.variables;

import mousquetaires.languages.internal.InternalEntity;
import mousquetaires.languages.internal.types.InternalType;


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
