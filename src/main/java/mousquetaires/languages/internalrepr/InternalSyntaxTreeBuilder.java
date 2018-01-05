package mousquetaires.languages.internalrepr;

import com.google.common.collect.ImmutableList;
import mousquetaires.patterns.Builder;

import java.util.Stack;


public class InternalSyntaxTreeBuilder extends Builder<InternalEntity> {

    private final ImmutableList.Builder<InternalEntity> roots;

    private Stack<InternalEntity> currentEntities;

    public InternalSyntaxTreeBuilder() {
        roots = new ImmutableList.Builder<>();
        currentEntities = new Stack<>();
    }

    public InternalSyntaxTree build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        setBuilt();
        return new InternalSyntaxTree(this);
    }

    //public void addRoot(InternalEntity root) {
    //    if (isBuilt()) {
    //        throw new RuntimeException(getAlreadyFinishedMessage());
    //    }
    //    roots.add(root);
    //}

    public void addEntity(InternalEntity entity) {
        currentEntities.add(entity);
    }



    public ImmutableList<InternalEntity> getRoots() {
        if (!isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        return roots.build();
    }
}
