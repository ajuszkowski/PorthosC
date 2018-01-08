package mousquetaires.languages.ytree;

import com.google.common.collect.ImmutableList;

import java.util.List;


public class InternalSyntaxTree implements YEntity {

    private final ImmutableList<YEntity> roots;

    public InternalSyntaxTree(InternalSyntaxTreeBuilder builder) {
        this.roots = builder.getRoots();
    }

    public List<YEntity> getRoots() {
        return roots;
    }
}
