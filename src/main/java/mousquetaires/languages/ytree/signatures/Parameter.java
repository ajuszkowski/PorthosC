package mousquetaires.languages.ytree.signatures;

import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.types.InternalType;


public class Parameter extends YExpression {
    public final InternalType type;
    public final String name;

    public Parameter(InternalType type, String name) {
        this.type = type;
        this.name = name;
    }
}
