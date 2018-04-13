package mousquetaires.languages.converters.toxgraph.hooks;

import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;

import java.util.function.BiFunction;


public class InterceptionAction {

    private final BiFunction<XMemoryUnit, XMemoryUnit[], ? extends XEntity> action;

    InterceptionAction(BiFunction<XMemoryUnit, XMemoryUnit[], ? extends XEntity> action) {
        this.action = action;
    }

    public XEntity execute(XMemoryUnit receiver, XMemoryUnit... arguments) {
        return action.apply(receiver, arguments);
    }
}
