package mousquetaires.languages.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.ytree.statements.YStatement;
import mousquetaires.patterns.Builder;


public class YSyntaxTreeBuilder extends Builder<YSyntaxTree> {

    private final ImmutableList.Builder<YStatement> roots;

    public YSyntaxTreeBuilder() {
        this.roots = new ImmutableList.Builder<>();
    }

    @Override
    public YSyntaxTree build() {
        return new YSyntaxTree(this.buildRoots());
    }

    public void addRoot(YStatement root) {
        this.roots.add(root);
    }

    public ImmutableList<YStatement> buildRoots() {
        return roots.build();
    }
}
