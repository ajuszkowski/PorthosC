package mousquetaires.languages.common.types;

import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public enum YXTypeName implements YEntity {
    Void,
    Char,
    Short,
    Int,
    Long,
    LongLong,
    Float,
    Double,
    LongDouble,
    Bool,
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
    public YEntity copy() {
        return this;  // for singletons it's safe to return the value while cloning
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        String space = "";
        for (char c : this.name().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                builder.append(c);
            } else {
                builder.append(space).append(Character.toLowerCase(c));
                space = " ";
            }
        }
        return builder.toString();
    }
}
