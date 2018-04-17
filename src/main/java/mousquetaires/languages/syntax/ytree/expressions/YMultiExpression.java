package mousquetaires.languages.syntax.ytree.expressions;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.citation.CodeLocation;

import java.util.Objects;


public abstract class YMultiExpression implements YExpression {

    private final CodeLocation location;
    private final int pointerLevel;
    private final ImmutableList<YExpression> elements;

    protected YMultiExpression(CodeLocation location, YExpression... elements) {
        this(location, 0, elements);
    }

    protected YMultiExpression(CodeLocation location, int pointerLevel, YExpression... elements) {
        this(location, pointerLevel, ImmutableList.copyOf(elements));
    }

    protected YMultiExpression(CodeLocation location, int pointerLevel, ImmutableList<YExpression> elements) {
        this.pointerLevel = pointerLevel;
        this.elements = elements;
        this.location = location;
    }

    protected ImmutableList<YExpression> getElements() {
        return elements;
    }

    @Override
    public int getPointerLevel() {
        return pointerLevel;
    }

    @Override
    public CodeLocation codeLocation() {
        return location;
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
