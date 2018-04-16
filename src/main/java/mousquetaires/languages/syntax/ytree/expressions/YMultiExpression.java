package mousquetaires.languages.syntax.ytree.expressions;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YAtom;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


public abstract class YMultiExpression implements YExpression {

    private final int pointerLevel;
    private final ImmutableList<YExpression> elements;

    protected YMultiExpression(YExpression... elements) {
        this(0, elements);
    }

    protected YMultiExpression(int pointerLevel, YExpression... elements) {
        this(pointerLevel, ImmutableList.copyOf(elements));
    }

    protected YMultiExpression(int pointerLevel, ImmutableList<YExpression> elements) {
        this.pointerLevel = pointerLevel;
        this.elements = elements;
    }

    protected ImmutableList<YExpression> getElements() {
        return elements;
    }

    @Override
    public int getPointerLevel() {
        return pointerLevel;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YMultiExpression)) return false;
        YMultiExpression that = (YMultiExpression) o;
        return Objects.equals(getElements(), that.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElements());
    }
}
