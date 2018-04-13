package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.common.NamedAtom;


public interface XLvalueMemoryUnit extends XMemoryUnit, NamedAtom {
    boolean isResolved();
}
