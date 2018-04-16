package mousquetaires.languages.syntax.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Collection;


public class YSyntaxTree implements YEntity {

    private final ImmutableList<YEntity> roots;

    public YSyntaxTree(ImmutableList<YEntity> roots) {
        this.roots = roots;
    }

    public YSyntaxTree(YEntity... statements) {
        this.roots = ImmutableList.copyOf(statements);
    }

    public YSyntaxTree(Collection<YEntity> statements) {
        this.roots = ImmutableList.copyOf(statements);
    }

    public ImmutableList<YEntity> getRoots() {
        return roots;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
