package mousquetaires.languages.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.ytree.statements.YStatement;

import java.util.List;


public class YSyntaxTree implements YEntity {

    private final ImmutableList<YEntity> roots;

    public YSyntaxTree(YSyntaxTreeBuilder builder) {
        this.roots = builder.getRoots();
    }

    public YSyntaxTree(YStatement... statements) {
        this.roots = ImmutableList.copyOf(statements);
    }

    public List<YEntity> getRoots() {
        return roots;
    }
}
