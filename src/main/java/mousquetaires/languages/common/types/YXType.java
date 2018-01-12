package mousquetaires.languages.common.types;

import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.xrepr.XEntity;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;

// TODO: name this class as "YXPrimitiveType" and add support for custom types (structs, enums, classes)

/** type as in LLVM: see https://llvm.org/docs/LangRef.html#integer-type */
public class YXType implements YEntity, XEntity {

    public final String name;
    public final YXTypeSpecifier specifier;
    public final int pointerLevel;

    YXType(String name, int pointerLevel) {
        this(name, YXTypeSpecifier.Signed, pointerLevel);
    }

    YXType(String name, YXTypeSpecifier specifier, int pointerLevel) {
        this.name = name;
        this.specifier = specifier;
        this.pointerLevel = pointerLevel;
    }

    public YXType withPointerLevel(int newPointerLevel) {
        return new YXType(name, specifier, newPointerLevel);
    }

    public YXType withSpecifier(YXTypeSpecifier newSpecifier) {
        return new YXType(name, newSpecifier, pointerLevel);
    }

    public YXType asUnsigned() {
        return new YXType(name, YXTypeSpecifier.Unsigned, pointerLevel);
    }


    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(specifier);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YXType copy() {
        return new YXType(name, specifier, pointerLevel);
    }

    @Override
    public String toString() {
        return specifier + " " + name;
    }


    // see https://os.mbed.com/handbook/C-Data-Types
    // or http://en.cppreference.com/w/cpp/language/types


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YXType)) return false;
        YXType yType = (YXType) o;
        return pointerLevel == yType.pointerLevel &&
               Objects.equals(name, yType.name) &&
               specifier == yType.specifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, specifier, pointerLevel);
    }
}
