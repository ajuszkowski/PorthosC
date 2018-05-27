package mousquetaires.languages.syntax.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Collection;


public class YSyntaxTree implements YEntity {

    private final Origin origin;
    private final ImmutableList<YEntity> roots;

    public YSyntaxTree(Origin origin, ImmutableList<YEntity> roots) {
        this.roots = roots;
        this.origin = origin;
    }

    public YSyntaxTree(Origin origin, YEntity... statements) {
        this(origin, ImmutableList.copyOf(statements));
    }

    public YSyntaxTree(Origin origin, Collection<YEntity> statements) {
        this(origin, ImmutableList.copyOf(statements));
    }

    public ImmutableList<YEntity> getRoots() {
        return roots;
    }

    @Override
    public Origin origin() {
        return origin;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
