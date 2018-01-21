package mousquetaires.types;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.xrepr.XEntity;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;

// TODO: process this class as "YXPrimitiveType" and add support for custom types (structs, enums, classes)

/** returnType as in LLVM: see https://llvm.org/docs/LangRef.html#integer-type */
public class ZType implements YEntity, XEntity {

    // TODO: move this to Y and add low-level bitvector representation to X

    public final String name;
    public final ZTypeSpecifier specifier;
    public final int pointerLevel;

    ZType(String name, int pointerLevel) {
        this(name, ZTypeSpecifier.Signed, pointerLevel);
    }

    ZType(String name, ZTypeSpecifier specifier, int pointerLevel) {
        this.name = name;
        this.specifier = specifier;
        this.pointerLevel = pointerLevel;
    }

    public ZType withPointerLevel(int newPointerLevel) {
        return new ZType(name, specifier, newPointerLevel);
    }

    public ZType withSpecifier(ZTypeSpecifier newSpecifier) {
        return new ZType(name, newSpecifier, pointerLevel);
    }

    public ZType asUnsigned() {
        return new ZType(name, ZTypeSpecifier.Unsigned, pointerLevel);
    }


    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(specifier);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public ZType copy() {
        return new ZType(name, specifier, pointerLevel);
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
        if (!(o instanceof ZType)) return false;
        ZType yType = (ZType) o;
        return pointerLevel == yType.pointerLevel &&
               Objects.equals(name, yType.name) &&
               specifier == yType.specifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, specifier, pointerLevel);
    }
}
