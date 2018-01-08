package mousquetaires.languages.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.patterns.Builder;


public class InternalSyntaxTreeBuilder extends Builder<InternalSyntaxTree> {

    private final ImmutableList.Builder<YEntity> roots;

    public InternalSyntaxTreeBuilder() {
        this.roots = new ImmutableList.Builder<>();
    }

    @Override
    public InternalSyntaxTree build() {
        return new InternalSyntaxTree(this);
    }

    public void addRoot(YEntity root) {
        this.roots.add(root);
    }

    public ImmutableList<YEntity> getRoots() {
        return roots.build();
    }
}
