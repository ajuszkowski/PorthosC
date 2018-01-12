package mousquetaires.languages.ytree.types;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public enum YPrimitiveTypeSpecifier implements YEntity {
    Signed,
    Unsigned,;

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom();
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
