package mousquetaires.languages.ytree.expressions;

import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public abstract class YExpression implements YEntity {
    // TODO: store location in the original code

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom();
    }

}
