package mousquetaires.languages.cmin.tokens;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;


/**
 * Note, this class implements InternalEntity, although it does not belongs to the type system of Internal language.
 * This is done in order to simplify parsing types in CminToInternalTransformer visitor, where this Cmin-type will be
 * converted to Internal-type on the last stage, when the assignee identifier is determined.
 */
public class CminIdentifier extends InternalExpression {
    private final String value;

    public CminIdentifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "cmin_keyword(" + value + ")";
    }
}
