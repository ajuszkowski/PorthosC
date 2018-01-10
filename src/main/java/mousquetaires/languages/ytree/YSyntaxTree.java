package mousquetaires.languages.ytree;

import com.google.common.collect.ImmutableList;

import java.util.List;


public class YSyntaxTree implements YEntity {

    private final ImmutableList<YEntity> roots;

    public YSyntaxTree(YSyntaxTreeBuilder builder) {
        this.roots = builder.getRoots();
    }

    public List<YEntity> getRoots() {
        return roots;
    }
}
