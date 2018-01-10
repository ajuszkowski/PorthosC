package mousquetaires.languages.ytree.expressions;

import mousquetaires.languages.cmin.transformer.tokens.CminKeyword;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.utils.exceptions.ArgumentNullException;

import java.util.Objects;


public class YConstant extends YExpression {

    protected final Object value;
    protected final YType type;

    public YConstant(Object value, YType type) {
        this.value = value;
        this.type = type;
    }

    //public ArithExpr toZ3(MapSSA map, Context ctx) {
    //    return ctx.mkInt(bitness);
    //}
    //
    //public Set<Register> getRegs() {
    //    return new HashSet<Register>();
    //}

    public static YConstant tryParse(String text){
        if (text == null) {
            throw new ArgumentNullException("text");
        }

        // Integer:
        try {
            int value = Integer.parseInt(text);
            return YConstant.createInteger(value);
        } catch (NumberFormatException e) { }

        // String (char array) :
        // use StringBuilder

        // TODO: try other known types.
        return null;
    }

    public static YConstant createInteger(int value) {
        return new YConstant(value, CminKeyword.convert(CminKeyword.Int));
    }

    public static YConstant newBooleanConstant(boolean value) {
        return new YConstant(value, CminKeyword.convert(CminKeyword.Bool));
    }

    public static YConstant newCharConstant(char value) {
        return new YConstant(value, CminKeyword.convert(CminKeyword.Char));
    }

    @Override
    public String toString() {
        return value + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YConstant)) return false;
        YConstant that = (YConstant) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
