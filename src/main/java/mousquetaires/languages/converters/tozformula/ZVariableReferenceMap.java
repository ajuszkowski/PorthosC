package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;

import java.util.HashMap;
import java.util.Map;


public class ZVariableReferenceMap extends HashMap<XMemoryUnit, Integer> {

    public ZVariableReferenceMap() {
    }

    public ZVariableReferenceMap(Map<? extends XMemoryUnit, ? extends Integer> m) {
        super(m);
    }
}
