package mousquetaires.languages.syntax.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.visitors.YtreeVisitor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;


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

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return roots.iterator();
    }

    public List<YEntity> getRoots() {
        return roots;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YEntity copy() {
        return new YSyntaxTree(roots);
    }
}
