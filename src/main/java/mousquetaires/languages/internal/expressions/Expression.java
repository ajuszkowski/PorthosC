package mousquetaires.languages.internal.expressions;

import mousquetaires.languages.internal.AbstractEntity;


public abstract class Expression implements AbstractEntity {
    // TODO: convert to location in the original code
    public final String originalExpression;

    public Expression(String originalExpression) {
        this.originalExpression = originalExpression;
    }
}
