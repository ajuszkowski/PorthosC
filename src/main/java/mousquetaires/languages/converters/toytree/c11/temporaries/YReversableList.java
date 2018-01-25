package mousquetaires.languages.converters.toytree.c11.temporaries;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.NotSupportedException;
import mousquetaires.utils.structures.ReversableList;

import java.util.ArrayList;
import java.util.Iterator;


class YReversableList<T> extends ReversableList<T> implements YTempEntity {

    protected YReversableList() {
        super(new ArrayList<>());
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(this);
    }

    @Override
    public <S> S accept(YtreeVisitor<S> visitor) {
        // todo: or not implemented exc
        throw new NotSupportedException(getClass().getName() + " is temporary entity.");
    }

    @Override
    public YEntity copy() {
        throw new NotSupportedException();
    }
}
