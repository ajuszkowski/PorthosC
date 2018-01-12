package mousquetaires.languages.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.statements.YStatement;

import java.util.Iterator;
import java.util.List;


public class YSyntaxTree implements YEntity {

    private final ImmutableList<YEntity> roots;

    public YSyntaxTree(YSyntaxTreeBuilder builder) {
        this.roots = builder.getRoots();
    }

    public YSyntaxTree(YStatement... statements) {
        this.roots = ImmutableList.copyOf(statements);
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return getRoots().iterator();
    }

    public List<YEntity> getRoots() {
        return roots;
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }
}
