package mousquetaires.languages.internalrepr.expressions;

import mousquetaires.languages.internalrepr.InternalEntity;


public abstract class InternalExpression implements InternalEntity {
    // TODO: convert to location in the original code
    public final String originalExpression;

    public InternalExpression(String originalExpression) {
        this.originalExpression = originalExpression;
    }
}
