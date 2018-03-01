package mousquetaires.languages.syntax.ytree.expressions;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


public abstract class YMultiExpression implements YExpression {

    private final ImmutableList<YExpression> elements;

    public YMultiExpression(YExpression... elements) {
        this.elements = ImmutableList.copyOf(elements);
    }

    protected YMultiExpression(ImmutableList<YExpression> elements) {
        this.elements = elements;
    }

    //protected YMultiExpression(Collection<YExpression> elements) {
    //    this.elements = ImmutableList.copyOf(elements);
    //}

    protected ImmutableList<YExpression> getElements() {
        return elements;
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
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
