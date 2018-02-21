package mousquetaires.languages.syntax.smt;

import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;


public class ZVariable extends ZExpression {
    final XMemoryUnit memoryUnit;
    final int uniqueIndex;

    public ZVariable(XMemoryUnit memoryUnit, int uniqueIndex) {
        this.memoryUnit = memoryUnit;
        this.uniqueIndex = uniqueIndex;
    }
}
