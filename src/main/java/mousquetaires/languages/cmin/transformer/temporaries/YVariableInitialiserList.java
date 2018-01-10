package mousquetaires.languages.cmin.transformer.temporaries;

import mousquetaires.languages.ytree.YEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class YVariableInitialiserList implements YEntity, Iterable<YVariableInitialiser> { //Iterable<YVariableInitialiser> {
    public final List<YVariableInitialiser> values = new ArrayList<>();

    //public void add(YVariableRef variable, YExpression initialisation)
    public void add(YVariableInitialiser initialiser) {
        values.add(initialiser);
    }

    public void addAll(Collection<YVariableInitialiser> initialiserList) {
        values.addAll(initialiserList);
    }

    @Override
    public Iterator<YVariableInitialiser> iterator() {
        return values.iterator();
    }
}
