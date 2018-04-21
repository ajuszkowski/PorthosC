package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.exceptions.ArgumentNullException;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Objects;


public class YConstant extends YAtomBase {

    private final Object value;
    private final YType type;

    private YConstant(CodeLocation location, Object value, YType type) {
        super(location, Kind.Global);
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public YType getType() {
        return type;
    }

    @Override
    public YAtom withPointerLevel(int level) {
        throw new NotSupportedException("constants cannot be pointers");
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return /*"(" + getType() + ")" +*/ ""+getValue();
    }

    // ... todo: other types...


    public static YConstant fromValue(int value) {
        return new YConstant(CodeLocation.empty, value, new YMockType()); //YTypeFactory.getPrimitiveType(YTypeName.Int));
    }

    public static YConstant fromValue(boolean value) {
        return new YConstant(CodeLocation.empty, value, new YMockType()); //YTypeFactory.getPrimitiveType(YTypeName.Bool));
    }

    public static YConstant fromValue(float value) {
        return new YConstant(CodeLocation.empty, value, new YMockType()); //YTypeFactory.getPrimitiveType(YTypeName.Float));
    }

    public static YConstant tryParse(String text) {
        if (text == null) {
            throw new ArgumentNullException();
        }

        // Integer:
        try {
            int value = Integer.parseInt(text);
            return fromValue(value);
        }
        catch (NumberFormatException e) { }
        // Float:
        try {
            float value = Float.parseFloat(text);
            return fromValue(value);
        }
        catch (NumberFormatException e) { }
        // Bool:
        {
            // TODO: set up good parsing of the keywords of C language as a separate module with 'C' in name
            if (text.equals("true")) {
                return fromValue(true);
            }
            else if (text.equals("false")) {
                return fromValue(false);
            }
        }

        // String (as char array) :
        // use StringBuilder

        // TODO: try other known types.
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof YConstant)) { return false; }
        if (!super.equals(o)) { return false; }
        YConstant yConstant = (YConstant) o;
        return Objects.equals(getValue(), yConstant.getValue()) &&
                Objects.equals(getType(), yConstant.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue(), getType());
    }
}
