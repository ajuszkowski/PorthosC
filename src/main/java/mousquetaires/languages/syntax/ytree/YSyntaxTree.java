package mousquetaires.languages.syntax.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Collection;


public class YSyntaxTree implements YEntity {

    private final Origin location;
    private final ImmutableList<YEntity> roots;

    public YSyntaxTree(Origin location, ImmutableList<YEntity> roots) {
        this.roots = roots;
        this.location = location;
    }
    public YSyntaxTree(Origin location, YEntity... statements) {

        this(location, ImmutableList.copyOf(statements));
    }

    public YSyntaxTree(Origin location, Collection<YEntity> statements) {
        this(location, ImmutableList.copyOf(statements));
    }

    public ImmutableList<YEntity> getRoots() {
        return roots;
    }

    @Override
    public Origin codeLocation() {
        return location;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
