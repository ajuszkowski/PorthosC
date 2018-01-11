package mousquetaires.languages.ytree.signatures;

import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.types.YType;


public class Parameter extends YExpression {
    public final YType type;
    public final String name;

    public Parameter(YType type, String name) {
        this.type = type;
        this.name = name;
    }
}
