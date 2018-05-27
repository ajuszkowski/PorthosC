package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.YEntity;


public interface YOperator extends YEntity {

    @Override
    default Origin codeLocation() {
        return Origin.empty;
    }
}
