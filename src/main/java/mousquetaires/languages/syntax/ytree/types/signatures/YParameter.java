package mousquetaires.languages.syntax.ytree.types.signatures;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public class YParameter extends YVariableRef {

    private final Type type;

    public YParameter(Kind kind, String name, Type type) {
        super(kind, name);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public YParameter withKind(Kind kind) {
        return new YParameter(kind, getName(), getType());
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
