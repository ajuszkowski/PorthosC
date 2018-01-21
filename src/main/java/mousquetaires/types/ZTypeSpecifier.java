package mousquetaires.types;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


// TODO: remove it
public enum ZTypeSpecifier implements YEntity {
    Signed,
    Unsigned,
    ;

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public ZTypeSpecifier copy() {
        return this;  // for singletons it's safe to return the value while cloning
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
