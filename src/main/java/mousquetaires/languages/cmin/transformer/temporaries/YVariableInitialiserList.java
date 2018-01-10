package mousquetaires.languages.cmin.transformer.temporaries;

import mousquetaires.languages.ytree.YEntity;

import java.util.*;


public class YVariableInitialiserList implements YEntity, Iterable<YVariableInitialiser> { //Iterable<YVariableInitialiser> {
    private final List<YVariableInitialiser> values = new ArrayList<>();

    public void add(YVariableInitialiser initialiser) {
        values.add(initialiser);
    }

    public void addAll(YVariableInitialiserList initialiserList) {
        values.addAll(initialiserList.values);
    }

    @Override
    public Iterator<YVariableInitialiser> iterator() {
        return new Iterator<>() {
            private int currentIndex = values.size() - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public YVariableInitialiser next() {
                return values.get(currentIndex--);
            }
        };
    }
}
