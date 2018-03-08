package mousquetaires.languages.syntax.ytree.temporaries;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.patterns.Builder;

import java.util.Collection;
import java.util.Iterator;


public class YTempListBuilder<T extends YEntity>
        extends Builder<ImmutableList<T>>
        implements YTempEntity {
    private final ImmutableList.Builder<T> values;

    public YTempListBuilder() {
        this.values = new ImmutableList.Builder<>();
    }

    @Override
    public ImmutableList<T> build() {
        // TODO: throw if values is empty
        return values.build();
    }

    public void add(T entity) {
        add(entity, values);
    }

    public void addAll(Collection<T> entities) {
        addAll(entities, values);
    }

    public void addAll(YTempListBuilder<T> builder) {
        addAll(builder.build(), values);
    }

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
