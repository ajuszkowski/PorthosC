package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public interface XMemoryUnit extends XEntity {

    Type getType();

    <T> T accept(XMemoryUnitVisitor<T> visitor);
}
