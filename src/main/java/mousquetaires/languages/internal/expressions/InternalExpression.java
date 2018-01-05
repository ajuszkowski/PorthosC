package mousquetaires.languages.internal.expressions;

import mousquetaires.languages.internal.InternalEntity;


public abstract class InternalExpression implements InternalEntity {
    // TODO: convert to location in the original code
    public final String originalExpression;

    public InternalExpression(String originalExpression) {
        this.originalExpression = originalExpression;
    }
}
