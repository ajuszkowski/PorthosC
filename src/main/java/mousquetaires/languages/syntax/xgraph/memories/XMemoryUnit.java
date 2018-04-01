package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.common.Bitness;
import mousquetaires.languages.common.NamedAtom;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public interface XMemoryUnit extends XEntity {

    Bitness getBitness();

    <T> T accept(XMemoryUnitVisitor<T> visitor);

}
