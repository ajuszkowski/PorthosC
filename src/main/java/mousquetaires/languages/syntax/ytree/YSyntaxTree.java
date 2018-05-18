package mousquetaires.languages.syntax.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Collection;


public class YSyntaxTree implements YEntity {

    private final CodeLocation location;
    private final ImmutableList<YEntity> roots;

    public YSyntaxTree(CodeLocation location, ImmutableList<YEntity> roots) {
        this.roots = roots;
        this.location = location;
    }
    public YSyntaxTree(CodeLocation location, YEntity... statements) {

        this(location, ImmutableList.copyOf(statements));
    }

    public YSyntaxTree(CodeLocation location, Collection<YEntity> statements) {
        this(location, ImmutableList.copyOf(statements));
    }

    public ImmutableList<YEntity> getRoots() {
        return roots;
    }

    @Override
    public CodeLocation codeLocation() {
        return location;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
