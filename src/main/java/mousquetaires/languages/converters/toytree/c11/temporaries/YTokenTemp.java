package mousquetaires.languages.converters.toytree.c11.temporaries;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;

import java.util.Iterator;


public class YTokenTemp implements YEntity {

    private final String value;

    public YTokenTemp(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new IllegalStateException();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new IllegalStateException();
    }

    @Override
    public YEntity copy() {
        throw new IllegalStateException();
    }
}
