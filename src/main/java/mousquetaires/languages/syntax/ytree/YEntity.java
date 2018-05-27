package mousquetaires.languages.syntax.ytree;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


public interface YEntity {

    <T> T accept(YtreeVisitor<T> visitor);

    Origin codeLocation();
}