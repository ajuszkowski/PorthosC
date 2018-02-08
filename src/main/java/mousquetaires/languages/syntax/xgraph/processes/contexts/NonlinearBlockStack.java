package mousquetaires.languages.syntax.xgraph.processes.contexts;

import java.util.ArrayList;
import java.util.Stack;


public class NonlinearBlockStack extends Stack<NonlinearBlock> {

    public void push(NonlinearBlockKind kind) {
        super.push(new NonlinearBlock(kind));
    }

    public Iterable<NonlinearBlock> getUninitialised() {
        //return this.stream().takeWhile(NonlinearBlock::isInitialised).collect(Collectors.toSet());
        ArrayList<NonlinearBlock> result = new ArrayList<>(size());
        for (int i = 0; i < size(); ++i) {
            NonlinearBlock nonlinearBlock = get(i);
            if (nonlinearBlock.hasTrueBranch()) {
                break;
            }
            result.add(nonlinearBlock);
        }
        return result;
    }
}
