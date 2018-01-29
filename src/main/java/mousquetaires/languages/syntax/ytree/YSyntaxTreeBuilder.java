package mousquetaires.languages.syntax.ytree;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.statements.YFakeStatement;
import mousquetaires.utils.Pair;
import mousquetaires.utils.patterns.Builder;

import java.util.Stack;


public class YSyntaxTreeBuilder extends Builder<YSyntaxTree> {

    private final ImmutableList.Builder<YEntity> roots;
    private final Stack<Pair<YFakeStatement, YFakeStatement>> loopContextStack = new Stack<>();
    private YFakeStatement currentFunctionExitNode;

    public YSyntaxTreeBuilder() {
        this.roots = new ImmutableList.Builder<>();
    }

    @Override
    public YSyntaxTree build() {
        return new YSyntaxTree(this.roots.build());
    }

    //public YFakeStatement currentFunctionExitNode() {
    //    if (currentFunctionExitNode == null) {
    //        throw new IllegalStateException("Attempt to exit function context without entering into any");
    //    }
    //    return currentFunctionExitNode;
    //}
    //
    //public YFakeStatement startFunctionDefinition() {
    //    return (currentFunctionExitNode = YFakeStatement.create());
    //}
    //
    //public void finishFunctionDefinition() {
    //    currentFunctionExitNode = null;
    //}
    //
    //public Pair<YFakeStatement, YFakeStatement> enterLoopContext() {
    //    Pair<YFakeStatement, YFakeStatement> pair =
    //            new Pair<>(YFakeStatement.create(), YFakeStatement.create());
    //    loopContextStack.push(pair);
    //    return pair;  // TODO: check that references still work there
    //}
    //
    //public void exitLoopContext() {
    //    loopContextStack.pop();
    //}
    //
    //public YFakeStatement currentLoopEntryNode() {
    //    // TODO: checks on emptiness
    //    return loopContextStack.peek().first();
    //}
    //
    //public YFakeStatement currentLoopExitNode() {
    //    // TODO: checks on emptiness
    //    return loopContextStack.peek().second();
    //}


    public void add(YEntity root) {
        add(root, roots);
    }

    public void addAll(Iterable<YEntity> rootsCollection) {
        addAll(rootsCollection, this.roots);
    }
}
