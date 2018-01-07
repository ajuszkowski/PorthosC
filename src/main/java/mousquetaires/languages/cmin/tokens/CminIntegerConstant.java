package mousquetaires.languages.cmin.tokens;


/**
 * Note, this class implements InternalEntity, although it does not belongs to the type system of Internal language.
 * This is done in order to simplify parsing types in CminToInternalTransformer visitor, where this Cmin-type will be
 * converted to Internal-type on the last stage, when the assignee identifier is determined.
 */
public class CminIntegerConstant extends CminConstant {   // TODO: use internal constant instead

    public final int value;

    public CminIntegerConstant(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
