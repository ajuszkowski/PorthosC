package mousquetaires.languages.internalrepr;

import com.google.common.collect.ImmutableList;
import mousquetaires.patterns.Builder;


public class InternalSyntaxTreeBuilder extends Builder<InternalSyntaxTree> {

    private final ImmutableList.Builder<InternalEntity> roots;

    public InternalSyntaxTreeBuilder() {
        this.roots = new ImmutableList.Builder<>();
    }

    @Override
    public InternalSyntaxTree build() {
        return new InternalSyntaxTree(this);
    }

    public void addRoot(InternalEntity root) {
        this.roots.add(root);
    }

    public ImmutableList<InternalEntity> getRoots() {
        return roots.build();
    }
}
