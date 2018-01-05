package mousquetaires.languages.internal.signatures;

import mousquetaires.languages.internal.expressions.InternalExpression;
import mousquetaires.languages.internal.types.InternalType;


public class Parameter extends InternalExpression {
    public final InternalType type;
    public final String name;

    public Parameter(String originalExpression, InternalType type, String name) {
        super(originalExpression);
        this.type = type;
        this.name = name;
    }
}
