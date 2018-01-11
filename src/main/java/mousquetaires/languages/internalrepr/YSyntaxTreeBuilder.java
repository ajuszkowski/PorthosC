package mousquetaires.languages.internalrepr;

import com.google.common.collect.ImmutableList;
import mousquetaires.patterns.Builder;


public class YSyntaxTreeBuilder extends Builder<YSyntaxTree> {

    private final ImmutableList.Builder<YEntity> roots;

    public YSyntaxTreeBuilder() {
        this.roots = new ImmutableList.Builder<>();
    }

    @Override
    public YSyntaxTree build() {
        return new YSyntaxTree(this);
    }

    public void addRoot(YEntity root) {
        this.roots.add(root);
    }

    public ImmutableList<YEntity> getRoots() {
        return roots.build();
    }
}
