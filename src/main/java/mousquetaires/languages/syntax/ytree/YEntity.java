package mousquetaires.languages.syntax.ytree;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


public interface YEntity {

    <T> T accept(YtreeVisitor<T> visitor);

    CodeLocation codeLocation();
}