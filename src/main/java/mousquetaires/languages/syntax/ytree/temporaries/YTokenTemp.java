package mousquetaires.languages.syntax.ytree.temporaries;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

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
    public Iterator<YEntity> getChildrenIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public YEntity copy() {
        throw new UnsupportedOperationException();
    }
}
