package mousquetaires.languages.ytree.types;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public enum YPrimitiveTypeSpecifier implements YEntity {
    Signed,
    Unsigned,
    ;

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YPrimitiveTypeSpecifier copy() {
        return this;  // for singletons it's safe to return the value while cloning
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
