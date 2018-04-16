package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


public class YParameter implements YAtom {

    private final YType type;
    private final YVariable variable;

    public YParameter(YType type, YVariable variable) {
        this.variable = variable.asGlobal();
        this.type = type;
    }

    public YType getType() {
        return type;
    }

    public YVariable getVariable() {
        return variable;
    }

    @Override
    public Kind getKind() {
        return getVariable().getKind();
    }

    @Override
    public int getPointerLevel() {
        return getVariable().getPointerLevel();
    }

    @Override
    public YParameter withPointerLevel(int level) {
        return new YParameter(getType(), getVariable().withPointerLevel(level));
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(getVariable(), getType());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
