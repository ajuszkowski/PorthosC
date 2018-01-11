package mousquetaires.languages.internalrepr.types;

import mousquetaires.languages.internalrepr.YEntity;

import java.util.Objects;


/** Integer type as in LLVM: see https://llvm.org/docs/LangRef.html#integer-type */
public class YType implements YEntity {

    public final String name;
    public final YPrimitiveTypeSpecifier specifier;
    public final int pointerLevel;

    YType(String name, int pointerLevel) {
        this(name, YPrimitiveTypeSpecifier.Signed, pointerLevel);
    }

    YType(String name, YPrimitiveTypeSpecifier specifier, int pointerLevel) {
        this.name = name;
        this.specifier = specifier;
        this.pointerLevel = pointerLevel;
    }

    public YType withPointerLevel(int newPointerLevel) {
        return new YType(name, specifier, newPointerLevel);
    }

    public YType withSpecifier(YPrimitiveTypeSpecifier newSpecifier) {
        return new YType(name, newSpecifier, pointerLevel);
    }

    public YType asUnsigned() {
        return new YType(name, YPrimitiveTypeSpecifier.Unsigned, pointerLevel);
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
        if (!(o instanceof YType)) return false;
        YType yType = (YType) o;
        return pointerLevel == yType.pointerLevel &&
               Objects.equals(name, yType.name) &&
               specifier == yType.specifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, specifier, pointerLevel);
    }
}
