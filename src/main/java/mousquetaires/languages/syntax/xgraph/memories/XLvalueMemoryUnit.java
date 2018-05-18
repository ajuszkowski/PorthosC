package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.common.NamedAtom;
import mousquetaires.languages.syntax.xgraph.XProcessLocalElement;


public interface XLvalueMemoryUnit extends XMemoryUnit, NamedAtom {
    boolean isResolved();
}
