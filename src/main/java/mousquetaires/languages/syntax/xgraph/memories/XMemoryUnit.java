package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.common.XType;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public interface XMemoryUnit extends XEntity {

    XType getType();

    <T> T accept(XMemoryUnitVisitor<T> visitor);
}
