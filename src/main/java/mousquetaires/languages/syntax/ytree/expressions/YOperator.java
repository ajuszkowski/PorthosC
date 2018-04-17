package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public interface YOperator extends YEntity {

    @Override
    default CodeLocation codeLocation() {
        return CodeLocation.empty;
    }
}
