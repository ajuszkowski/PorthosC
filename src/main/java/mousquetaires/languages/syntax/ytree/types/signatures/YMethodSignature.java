package mousquetaires.languages.syntax.ytree.types.signatures;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public class YMethodSignature implements YEntity {
    private final String name;
    private final YType returnType;
    private final YParameter[] parameters;

    public YMethodSignature(String name, YType returnType, YParameter[] parameters) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public YType getReturnType() {
        return returnType;
    }

    public YParameter[] getParameters() {
        return parameters;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YMethodSignature copy() {
        return new YMethodSignature(getName(), getReturnType(), getParameters());
    }
}
