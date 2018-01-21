package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


public abstract class YMultiExpression implements YExpression {

    private final YExpression[] elements;

    public YMultiExpression(YExpression... arguments) {
        this.elements = arguments;
    }

    protected YMultiExpression(Collection<YExpression> arguments) {
        this.elements = arguments.toArray(new YExpression[0]);
    }

    protected YExpression[] getElements() {
        return elements;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YMultiExpression)) return false;
        YMultiExpression that = (YMultiExpression) o;
        return Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getElements());
    }
}
