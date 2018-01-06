package mousquetaires.languages.cmin.transformer;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;


/**
 * Note, this class implements InternalEntity, although it does not belongs to the type system of Internal language.
 * This is done in order to simplify parsing types in CminToInternalTransformer visitor, where this Cmin-type will be
 * converted to Internal-type on the last stage, when the assignee identifier is determined.
 */
public class CminStringConstant extends InternalExpression {
    public final String value;

    public CminStringConstant(String value) {
        this.value = value;
    }
}
