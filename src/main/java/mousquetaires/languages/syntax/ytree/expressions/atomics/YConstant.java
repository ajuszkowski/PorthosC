package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YMemoryLocation;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.exceptions.ArgumentNullException;

import java.util.Iterator;
import java.util.Objects;


public class YConstant implements YMemoryLocation {

    private final Object value;
    private final YType type;

    private YConstant(Object value, YType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public YType getType() {
        return type;
    }

    public static YConstant fromValue(int value) {
        return new YConstant(value, new YMockType()); //YTypeFactory.getPrimitiveType(YTypeName.Int));
    }

    public static YConstant fromValue(boolean value) {
        return new YConstant(value, new YMockType()); //YTypeFactory.getPrimitiveType(YTypeName.Bool));
    }
    public static YConstant fromValue(float value) {
        return new YConstant(value, new YMockType()); //YTypeFactory.getPrimitiveType(YTypeName.Float));
    }

    // ... todo: other types...

    public static YConstant tryParse(String text) {
        if (text == null) {
            throw new ArgumentNullException();
        }

        // Integer:
        try {
            return fromValue(Integer.parseInt(text));
        } catch (NumberFormatException e) {
        }
        // Float:
        try {
            return fromValue(Float.parseFloat(text));
        } catch (NumberFormatException e) {
        }
        // Bool:
        try {
            return fromValue(Boolean.parseBoolean(text));
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
        return CollectionUtils.createIteratorFrom();
    }

    @Override
    public String toString() {
        return "(" + type + ")" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YConstant)) return false;
        YConstant yConstant = (YConstant) o;
        return Objects.equals(getValue(), yConstant.getValue()) &&
                Objects.equals(getType(), yConstant.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getType());
    }
}
