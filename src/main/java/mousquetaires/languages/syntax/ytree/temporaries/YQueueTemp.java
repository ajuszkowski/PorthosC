package mousquetaires.languages.syntax.ytree.temporaries;

import com.google.common.collect.ImmutableList;
import com.sun.javafx.font.PrismFontFactory;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Collection;
import java.util.List;


public abstract class YQueueTemp<T extends YEntity> implements YTempEntity {

    public abstract void add(T entity);

    public abstract List<T> getValues();

    public ImmutableList<T> buildValues() {
        return ImmutableList.copyOf(getValues());
    }

    public void addAll(Collection<T> entities) {
        getValues().addAll(entities);
    }

    @Override
    public <S> S accept(YtreeVisitor<S> visitor) {
        throw new UnsupportedOperationException();
    }
}
