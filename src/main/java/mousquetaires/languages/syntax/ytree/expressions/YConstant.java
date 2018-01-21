package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.types.ZType;
import mousquetaires.types.ZTypeFactory;
import mousquetaires.types.ZTypeName;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.ArgumentNullException;

import java.util.Iterator;
import java.util.Objects;


public class YConstant implements YExpression {

    private final Object value;
    private final ZType type;

    YConstant(Object value, ZType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public ZType getType() {
        return type;
    }

    public static YConstant fromValue(int value) {
        return new YConstant(value, ZTypeFactory.getPrimitiveType(ZTypeName.Int));
    }

    public static YConstant fromValue(boolean value) {
        return new YConstant(value, ZTypeFactory.getPrimitiveType(ZTypeName.Bool));
    }

    // ... todo: others...

    public static YConstant tryParse(String text) {
        if (text == null) {
            throw new ArgumentNullException();
        }

        // Integer:
        try {
            fromValue(Integer.parseInt(text));
        } catch (NumberFormatException e) {
        }

        // String (as char array) :
        // use StringBuilder

        // TODO: try other known types.
        return null;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YConstant copy() {
        return new YConstant(value, type);
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom();
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

    //public ArithExpr toZ3(MapSSA map, Context ctx) {
    //    return ctx.mkInt(bitness);
    //}
    //
    //public Set<Register> getRegs() {
    //    return new HashSet<Register>();
    //}

    // boolean constant:
        //public BoolExpr toZ3(MapSSA map, Context ctx) {
    //    //    if(bitness) {
    //    //        return ctx.mkTrue();
    //    //    }
    //    //    else {
    //    //        return ctx.mkFalse();
    //    //    }
    //    //}
}
