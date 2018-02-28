package mousquetaires.languages.syntax.ytree.temporaries;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Iterator;


public abstract class YTempEntityBase implements YTempEntity {

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S> S accept(YtreeVisitor<S> visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public YEntity copy() {
        throw new UnsupportedOperationException();
    }
}
