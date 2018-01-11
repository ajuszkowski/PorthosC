package mousquetaires.languages.internalrepr.signatures;

import mousquetaires.languages.internalrepr.expressions.YExpression;
import mousquetaires.languages.internalrepr.types.YType;


public class Parameter extends YExpression {
    public final YType type;
    public final String name;

    public Parameter(YType type, String name) {
        this.type = type;
        this.name = name;
    }
}
