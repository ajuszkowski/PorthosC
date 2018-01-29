package mousquetaires.languages.syntax.ytree.temporaries;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Iterator;


public class YTokenTemp extends YTempEntityBase {

    private final String value;

    public YTokenTemp(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new NotSupportedException();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new NotSupportedException();
    }

    @Override
    public YEntity copy() {
        throw new NotSupportedException();
    }
}
