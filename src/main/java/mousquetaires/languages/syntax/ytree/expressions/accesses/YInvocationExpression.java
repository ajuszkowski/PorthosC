package mousquetaires.languages.syntax.ytree.expressions.accesses;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YAtom;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.ImmutableUtils;


public class YInvocationExpression extends YMultiExpression {

    private final int elementsCount;
    // TODO: process signatures! firstly only function name. -- not here, on YIndexerExpression level

    //public YInvocationExpression(YExpression baseExpression, YExpression... arguments) {
    //    this(baseExpression, ImmutableList.copyOf(arguments));
    //}
    public YInvocationExpression(CodeLocation location, YExpression baseExpression, ImmutableList<YExpression> arguments) {
        this(location, baseExpression, arguments, 0);
    }

    private YInvocationExpression(CodeLocation location, YExpression baseExpression, ImmutableList<YExpression> arguments, int pointerLevel) {
        super(location, pointerLevel, ImmutableUtils.append(baseExpression, arguments));
        this.elementsCount = arguments.size() + 1;
    }

    public YExpression getBaseExpression() {
        return getElements().get(0);
    }

    public ImmutableList<YExpression> getArguments() {
        return getElements().subList(1, elementsCount);
    }

    @Override
    public YInvocationExpression withPointerLevel(int level) {
        return new YInvocationExpression(codeLocation(), getBaseExpression(), getArguments(), level);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getBaseExpression()).append('(');
        ImmutableList<YExpression> arguments = getArguments();
        int lastIndex = arguments.size() - 1;
        for (int i = 0; i < lastIndex; i++) {
            sb.append(arguments.get(i)).append(", ");
        }
        sb.append(arguments.get(lastIndex)).append(')');
        return sb.toString();
    }
}
