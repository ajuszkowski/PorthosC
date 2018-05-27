package mousquetaires.languages.syntax.wmodel;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;


public interface WEntity {

    boolean containsRecursion();

    <T> T accept(WmodelVisitor<T> visitor);

    Origin origin();
}
