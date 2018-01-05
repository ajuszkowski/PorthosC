package mousquetaires.languages.internalrepr;

import com.google.common.collect.ImmutableList;


public class InternalSyntaxTree implements InternalEntity {

    private final ImmutableList<InternalEntity> roots;

    public InternalSyntaxTree(InternalSyntaxTreeBuilder builder) {
        this.roots = builder.getRoots();
    }

    //@Override
    //public Iterable<InternalEntity> iterateChildren() {
    //    return roots;
    //}
}
