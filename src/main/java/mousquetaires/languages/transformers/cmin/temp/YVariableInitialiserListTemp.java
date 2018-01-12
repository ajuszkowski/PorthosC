package mousquetaires.languages.transformers.cmin.temp;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class YVariableInitialiserListTemp implements YTempEntity {
    public class ReversedVarInitCollection implements Iterable<YVariableInitialiserTemp> {
        private final List<YVariableInitialiserTemp> values = new ArrayList<>();

        public void add(YVariableInitialiserTemp initialiser) {
            values.add(initialiser);
        }

        public void addAll(ReversedVarInitCollection collection) {
            values.addAll(collection.values);
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
    }

    public final ReversedVarInitCollection initialisers = new ReversedVarInitCollection();

    public void add(YVariableInitialiserTemp initialiser) {
        initialisers.add(initialiser);
    }

    public void addAll(YVariableInitialiserListTemp initialiserList) {
        initialisers.addAll(initialiserList.initialisers);
    }


    @Override
    public Iterator<YVariableInitialiserTemp> getChildrenIterator() {
        return initialisers.iterator();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        // todo: or not implemented exc
        throw new NotSupportedException();
    }

    @Override
    public YEntity copy() {
        throw new NotSupportedException();
    }
}
