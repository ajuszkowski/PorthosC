package mousquetaires.languages.internalrepr;

import com.google.common.collect.ImmutableList;

import java.util.List;


public class InternalSyntaxTree implements InternalEntity {

    private final ImmutableList<InternalEntity> roots;

    public InternalSyntaxTree(InternalSyntaxTreeBuilder builder) {
        this.roots = builder.getRoots();
    }

    public List<InternalEntity> getRoots() {
        return roots;
    }
}
