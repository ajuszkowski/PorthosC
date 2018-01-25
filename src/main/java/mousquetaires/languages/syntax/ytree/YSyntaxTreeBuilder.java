package mousquetaires.languages.syntax.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.patterns.Builder;


public class YSyntaxTreeBuilder extends Builder<YSyntaxTree> {

    private final ImmutableList.Builder<YEntity> roots;

    public YSyntaxTreeBuilder() {
        this.roots = new ImmutableList.Builder<>();
    }

    @Override
    public YSyntaxTree build() {
        return new YSyntaxTree(this.buildRoots());
    }

    public void addRoot(YEntity root) {
        this.roots.add(root);
    }

    public ImmutableList<YEntity> buildRoots() {
        return roots.build();
    }
}
