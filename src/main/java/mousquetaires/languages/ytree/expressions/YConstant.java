package mousquetaires.languages.ytree.expressions;

import mousquetaires.languages.ytree.types.YType;
import mousquetaires.utils.exceptions.ArgumentNullException;

import java.util.Objects;


public class YConstant extends YExpression {
    protected final Object value;
    protected final YType type;

    YConstant(Object value, YType type) {
        this.value = value;
        this.type = type;
    }

    public static YConstant tryParse(String text) {
        if (text == null) {
            throw new ArgumentNullException();
        }

        // Integer:
        try {
            int value = Integer.parseInt(text);
            return YConstantFactory.newIntConstant(value);
        } catch (NumberFormatException e) {
        }

        // String (as char array) :
        // use StringBuilder

        // TODO: try other known types.
        return null;
    }

    @Override
    public String toString() {
        return value + ":" + type;
    }

    //public ArithExpr toZ3(MapSSA map, Context ctx) {
    //    return ctx.mkInt(bitness);
    //}
    //
    //public Set<Register> getRegs() {
    //    return new HashSet<Register>();
    //}

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
