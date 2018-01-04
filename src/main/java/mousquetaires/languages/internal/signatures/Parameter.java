package mousquetaires.languages.internal.signatures;

import mousquetaires.languages.internal.expressions.Expression;
import mousquetaires.languages.internal.types.Type;

public class Parameter extends Expression {
    public final Type type;
    public final String name;

    public Parameter(String originalExpression, Type type, String name) {
        super(originalExpression);
        this.type = type;
        this.name = name;
    }
}
