package mousquetaires.languages.syntax.ytree.types;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Iterator;


public class YMockType implements YType {

    @Override
    public Qualifier getQualifier() {
        return null;
    }

    @Override
    public Specifier getSpecifier() {
        return null;
    }

    @Override
    public int getPointerLevel() {
        return 0;
    }

    @Override
    public YType withPointerLevel(int newPointerLevel) {
        return this;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new NotImplementedException();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new NotImplementedException();

    }

    @Override
    public YEntity copy() {
        throw new NotImplementedException();
    }


    @Override
    public String toString() {
        return "mock_type";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof YMockType;
    }
}
