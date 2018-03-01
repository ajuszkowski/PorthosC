package mousquetaires.languages.syntax.ytree.types.signatures;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public class YParameter implements YEntity {

    private final YType type;
    private final String name;

    public YParameter(YType type, String name) {
        this.type = type;
        this.name = name;
    }

    public YType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YEntity copy() {
        return new YParameter(getType(), getName());
    }
}
