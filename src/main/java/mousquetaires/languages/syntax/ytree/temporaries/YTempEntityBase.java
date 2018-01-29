package mousquetaires.languages.syntax.ytree.temporaries;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Iterator;


public abstract class YTempEntityBase implements YTempEntity {

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new NotSupportedException();
    }

    @Override
    public <S> S accept(YtreeVisitor<S> visitor) {
        throw new NotSupportedException();
    }

    @Override
    public YEntity copy() {
        throw new NotSupportedException();
    }
}
