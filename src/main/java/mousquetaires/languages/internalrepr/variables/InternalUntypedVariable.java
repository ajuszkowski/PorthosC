package mousquetaires.languages.internalrepr.variables;


import mousquetaires.languages.internalrepr.types.InternalType;


public class InternalUntypedVariable extends InternalVariable {

    public InternalUntypedVariable(String name) {
        super(null, name );
    }

    public InternalVariable toTypedVariable(InternalType type) {
        return new InternalVariable(type, name);
    }
}
