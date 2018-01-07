package mousquetaires.languages.internalrepr.expressions;

import mousquetaires.languages.cmin.tokens.CminKeyword;
import mousquetaires.languages.internalrepr.types.InternalType;

import java.util.Objects;


public class InternalConstant extends InternalExpression {

    protected final Object value;
    protected final InternalType type;

    public InternalConstant(Object value, InternalType type) {
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


    public static InternalConstant newIntegerConstant(int value) {
        return new InternalConstant(value, CminKeyword.convert(CminKeyword.Int));
    }

    public static InternalConstant newBooleanConstant(boolean value) {
        return new InternalConstant(value, CminKeyword.convert(CminKeyword.Bool));
    }

    public static InternalConstant newCharConstant(char value) {
        return new InternalConstant(value, CminKeyword.convert(CminKeyword.Char));
    }


    @Override
    public String toString() {
        return value + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalConstant)) return false;
        InternalConstant that = (InternalConstant) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, type);
    }
}
