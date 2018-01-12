package mousquetaires.languages.transformers.cmin.temp;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class YVariableInitialiserListTemp
        implements YTempEntity, Iterable<YVariableInitialiserTemp> {

    private final List<YVariableInitialiserTemp> values = new ArrayList<>();

    public void add(YVariableInitialiserTemp initialiser) {
        values.add(initialiser);
    }

    public void addAll(YVariableInitialiserListTemp initialiserList) {
        values.addAll(initialiserList.values);
    }

    @Override
    public Iterator<YVariableInitialiserTemp> iterator() {
        return new Iterator<>() {
            private int currentIndex = values.size() - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public YVariableInitialiserTemp next() {
                return values.get(currentIndex--);
            }
        };
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(values.toArray(YEntity.class));
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        throw new NotSupportedException();
    }
}
