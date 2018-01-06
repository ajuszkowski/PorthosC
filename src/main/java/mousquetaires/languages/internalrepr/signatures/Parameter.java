package mousquetaires.languages.internalrepr.signatures;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;
import mousquetaires.languages.internalrepr.types.InternalType;


public class Parameter extends InternalExpression {
    public final InternalType type;
    public final String name;

    public Parameter(InternalType type, String name) {
        this.type = type;
        this.name = name;
    }
}
