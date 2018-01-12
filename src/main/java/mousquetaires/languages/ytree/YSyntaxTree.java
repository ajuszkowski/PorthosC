package mousquetaires.languages.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.statements.YStatement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class YSyntaxTree implements YEntity {

    private final ImmutableList<YStatement> roots;

    public YSyntaxTree(ImmutableList<YStatement> roots) {
        this.roots = roots;
    }

    public YSyntaxTree(YStatement... statements) {
        this.roots = ImmutableList.copyOf(statements);
    }

    public YSyntaxTree(Collection<YStatement> statements) {
        this.roots = ImmutableList.copyOf(statements);
    }

    @Override
    public Iterator<YStatement> getChildrenIterator() {
        return roots.iterator();
    }

    public List<YStatement> getRoots() {
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
