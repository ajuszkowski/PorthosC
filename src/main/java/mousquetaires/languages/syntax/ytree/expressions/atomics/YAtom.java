package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public interface YAtom extends YExpression {

    Kind getKind();

    @Override
    default Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom();
    }

    enum Kind {
        Local,
        Global,
        ;
    }
}
